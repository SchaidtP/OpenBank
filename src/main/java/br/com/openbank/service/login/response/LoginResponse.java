package br.com.openbank.service.login.response;

import lombok.Data;

import java.util.UUID;

@Data
public class LoginResponse {
    public UUID idClient;
    public String token;

    public LoginResponse(UUID idClient, String token){
        this.idClient = idClient;
        this.token = token;
    }
}
