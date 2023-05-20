package br.com.openbank.service.login;

import br.com.openbank.service.login.request.LoginRequest;
import br.com.openbank.service.login.response.LoginResponse;
import br.com.openbank.service.client.IClientService;
import br.com.openbank.service.security.IJwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements ILoginService {

    @Autowired
    private IClientService iClientService;

    @Autowired
    private IJwtService iJwtService;


    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        var client = iClientService.getClientByCpf(loginRequest.cpf);

        if(client == null){
            throw new Exception("Client not found");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(loginRequest.password, client.getPassword())){
            throw new Exception("Invalid credentials");
        }

        var token = iJwtService.generateToken(client.getId());

        return new LoginResponse(client.getId(), token);
    }
}
