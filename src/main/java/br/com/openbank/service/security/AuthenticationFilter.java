package br.com.openbank.service.security;

import br.com.openbank.service.client.IClientService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private IJwtService iJwtService;

    @Autowired
    private IClientService iClientService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (shouldBypassAuthentication(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");
        String idClient = request.getHeader("RequestedBy");

        if(token == null || idClient == null || !token.startsWith("Bearer ")){
            sendUnauthorizedResponse(response, "Client not authenticated!");
            return;
        }

        boolean isValidToken = false;

        try {
            isValidToken = iJwtService.isValidToken(token.substring(7), idClient);
        } catch (Exception e) {
            sendUnauthorizedResponse(response, e.getMessage());
            return;
        }

        if(isValidToken) {
            try {
                UUID UUIDClient = UUID.fromString(idClient);
                var client = iClientService.findClientById(UUIDClient);

                var authentication = new UsernamePasswordAuthenticationToken(client, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                sendUnauthorizedResponse(response, e.getMessage());
                return;
            }
        } else {
            sendUnauthorizedResponse(response, "Invalid token!");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean shouldBypassAuthentication(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String httpMethod = request.getMethod();

        if (servletPath.equals("/api/v1/client") && httpMethod.equals("POST")) {
            return true;
        }

        return servletPath.contains("swagger")
                || servletPath.contains("docs")
                || servletPath.equals("/api/v1/login");
    }


    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.getWriter().write(message);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
