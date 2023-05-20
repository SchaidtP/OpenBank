package br.com.openbank.service.account;

import br.com.openbank.service.account.request.AccountTransferMoneyRequest;
import br.com.openbank.service.account.response.GetAccountResponse;

import java.util.UUID;

public interface IAccountService {
    void createAccount(UUID idClient, Integer typeAccount) throws Exception;
    void deleteAccount(UUID idClient) throws Exception;
    void deposit(Double balance) throws Exception;
    void transferMoney(AccountTransferMoneyRequest accountTransferMoneyRequest) throws Exception;
    GetAccountResponse getAccount();
    void maintenanceFee();
}
