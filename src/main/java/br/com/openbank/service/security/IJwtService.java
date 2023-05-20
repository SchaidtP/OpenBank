package br.com.openbank.service.security;

import java.util.UUID;

public interface IJwtService {
    String generateToken(UUID idClient);
    boolean isValidToken(String token, String idClient);
}
