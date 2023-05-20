package br.com.openbank.service.account;

import br.com.openbank.model.entity.Account;
import br.com.openbank.model.enums.TypeAccount;
import br.com.openbank.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class AccountService implements IAccountService{

    @Autowired
    IAccountRepository iAccountRepository;

    public void createAccount(UUID idClient, Integer typeAccount) throws Exception {
        Account account = new Account(idClient, 0.0, TypeAccount.getTypeAccount(typeAccount), dateOneMonthLater());
        try {
            iAccountRepository.save(account);
            return;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    private LocalDate dateOneMonthLater(){
        LocalDate currentDate = LocalDate.now();
        return currentDate.plusMonths(1);
    }
}
