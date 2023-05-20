package br.com.openbank.service.account;

import br.com.openbank.model.embedded.CardCredit;
import br.com.openbank.model.entity.Account;
import br.com.openbank.model.enums.TypeAccount;
import br.com.openbank.model.enums.TypeCard;
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
        Account account = new Account(idClient, TypeAccount.getTypeAccount(typeAccount), dateOneMonthLater());
        try {
            iAccountRepository.save(account);
            return;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void deleteAccount(UUID idClient) throws Exception {
        var account = iAccountRepository.findAccountByIdClient(idClient).orElse(null);

        if (account.getBalance() < 0.0 || account.getBalance() > 0.0){
            throw new Exception("Unable to delete due to balance, please check it");
        }

        var cardCredit = (CardCredit) account.getCard(TypeCard.CREDIT);
        if(cardCredit != null){
            if(cardCredit.getInvoiceAmount() > 0.0) {
                throw new Exception("Impossible to delete customer, because open invoice on credit card");
            }
        }

        try {
            iAccountRepository.delete(account);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    private LocalDate dateOneMonthLater(){
        LocalDate currentDate = LocalDate.now();
        return currentDate.plusMonths(1);
    }
}
