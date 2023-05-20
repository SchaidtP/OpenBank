package br.com.openbank.service.card.debit;

import br.com.openbank.model.embedded.Card;
import br.com.openbank.model.embedded.CardCredit;
import br.com.openbank.model.embedded.CardDebit;
import br.com.openbank.model.entity.Client;
import br.com.openbank.model.enums.TypeCard;
import br.com.openbank.service.account.IAccountService;
import br.com.openbank.service.card.debit.response.GetCardDebitResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CardDebitService implements ICardDebitService {

    @Autowired
    IAccountService iAccountService;

    public void createCardDebit(Double limit) throws Exception {
        var account = iAccountService.findAccount();
        if(account.getCard(TypeCard.DEBIT) == null){
            try {
                account.addCard((Card) new CardDebit(limit));
                iAccountService.saveAccount(account);
            } catch (Exception e){
                throw new Exception(e.getMessage());
            }
        }
        throw new Exception("Account already has debit card");
    }

    public void deleteCardDebit() throws Exception {
        var account = iAccountService.findAccount();
        var cardDebit = ((CardDebit) account.getCard(TypeCard.DEBIT));
        if(cardDebit != null) {
            try {
                account.deleteCard((Card) cardDebit);
            } catch (Exception e){
                throw new Exception(e.getMessage());
            }
        }
        throw new Exception("Non-existent credit card");
    }

    public GetCardDebitResponse getCardDebit() throws Exception {
        var account = iAccountService.findAccount();
        var cardDebit = ((CardDebit) account.getCard(TypeCard.DEBIT));
        if(cardDebit != null) {
            return new GetCardDebitResponse(cardDebit.getNumber(), cardDebit.getFlag(), cardDebit.isActive(), getCardDebit().limitPerTransaction);
        }
        throw new Exception("Non-existent debit card");
    }

    public String activateAndDeactivateCardDebit() throws Exception {
        var account = iAccountService.findAccount();
        var cardDebit = ((CardDebit) account.getCard(TypeCard.DEBIT));

        if(cardDebit != null){
            if(cardDebit.isActive()){
                cardDebit.setActive(false);
                return "Deactivated debit card";
            }
            cardDebit.setActive(true);
            return "Activated debit card";
        }
        throw new Exception("Non-existent debit card");
    }

    public void buyCardDebit(Double value) throws Exception {
        var account = iAccountService.findAccount();
        var cardDebit = ((CardDebit) account.getCard(TypeCard.DEBIT));
        if(cardDebit != null){
            if(cardDebit.isActive() && cardDebit.getLimitPerTransaction() <= value){
                if(account.getBalance() >= value){
                    account.setBalance(account.getBalance() - value);
                    iAccountService.saveAccount(account);
                }
                throw new Exception("No account balance");
            }
            throw new Exception("Inactive debit card or exceeded limit");
        }
        throw new Exception("Non-existent debit card");
    }
}
