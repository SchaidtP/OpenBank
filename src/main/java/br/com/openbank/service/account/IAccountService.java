package br.com.openbank.service.account;

import java.util.UUID;

public interface IAccountService {
    void createAccount(UUID idClient, Integer typeAccount) throws Exception;
}
