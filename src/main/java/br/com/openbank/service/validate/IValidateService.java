package br.com.openbank.service.validate;

public interface IValidateService {
    boolean validateCpf(String cpf);
    boolean validateEmail(String email);
}
