package br.com.openbank.service.login.request;

import lombok.Data;

@Data
public class LoginRequest {
    public String cpf;
    public String password;
}
