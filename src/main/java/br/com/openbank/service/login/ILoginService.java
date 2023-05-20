package br.com.openbank.service.login;

import br.com.openbank.service.login.request.LoginRequest;
import br.com.openbank.service.login.response.LoginResponse;

public interface ILoginService {
    LoginResponse login(LoginRequest loginRequest) throws Exception;
}
