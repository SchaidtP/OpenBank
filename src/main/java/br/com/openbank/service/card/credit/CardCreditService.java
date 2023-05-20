package br.com.openbank.service.card.credit;

import br.com.openbank.model.embedded.Card;
import br.com.openbank.model.embedded.CardCredit;
import br.com.openbank.model.embedded.Purchase;
import br.com.openbank.model.entity.Client;
import br.com.openbank.model.enums.TypeCard;
import br.com.openbank.model.enums.TypeClient;
import br.com.openbank.service.account.IAccountService;
import br.com.openbank.service.card.credit.response.GetCardCreditResponse;
import br.com.openbank.service.card.credit.response.GetPurchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardCreditService implements ICardCreditService{

    @Autowired
    IAccountService iAccountService;

    public void createCardCredit() throws Exception {
        var client = ((Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        var account = iAccountService.findAccount();
        CardCredit cardCredit;
        if(account.getCard(TypeCard.CREDIT) == null){
            if(client.getTypeClient().equals(TypeClient.COMMON)){
                cardCredit = new CardCredit(1000.0);
            } else if (client.getTypeClient().equals(TypeClient.SUPER)) {
                cardCredit = new CardCredit(3000.0);
            } else {
                cardCredit = new CardCredit(12000.0);
            }

            try {
                account.addCard((Card) cardCredit);
                iAccountService.saveAccount(account);
            } catch (Exception e){
                throw new Exception(e.getMessage());
            }
        }
        throw new Exception("Account already has credit card");
    }

    public void deleteCardCredit() throws Exception {
        var account = iAccountService.findAccount();
        var cardCredit = ((CardCredit) account.getCard(TypeCard.CREDIT));
        if(cardCredit != null) {
            if(cardCredit.getInvoiceAmount() > 0){
                throw new Exception("Open invoice, impossible to delete card");
            }
            try {
                account.deleteCard((Card) cardCredit);
            } catch (Exception e){
                throw new Exception(e.getMessage());
            }
        }
        throw new Exception("Non-existent credit card");
    }

    public GetCardCreditResponse getCardCredit() throws Exception {
        var account = iAccountService.findAccount();
        var cardCredit = ((CardCredit) account.getCard(TypeCard.CREDIT));
        if(cardCredit == null){
            List<GetPurchase> getPurchases = new ArrayList<>();
            for (Purchase purchase : cardCredit.getPurchases()){
                GetPurchase toReceive = new GetPurchase();
                toReceive.setDate(purchase.getDate());
                toReceive.setValue(purchase.getValue());
                getPurchases.add(toReceive);
            }
            return new GetCardCreditResponse(cardCredit.getNumber(), cardCredit.getFlag(), cardCredit.isActive(), cardCredit.getLimit(), getPurchases, cardCredit.getDateDue(), cardCredit.getInvoiceAmount());
        }
        throw new Exception("Non-existent credit card");
    }

    public String activateAndDeactivateCardCredit() throws Exception {
        var account = iAccountService.findAccount();
        var cardCredit = ((CardCredit) account.getCard(TypeCard.CREDIT));

        if(cardCredit != null){
            if(cardCredit.isActive()){
                cardCredit.setActive(false);
                return "Deactivated credit card";
            }
            cardCredit.setActive(true);
            return "Activated credit card";
        }
        throw new Exception("Non-existent credit card");
    }

    public void buyCardCredit(Double value) throws Exception {
        var account = iAccountService.findAccount();
        var cardCredit = ((CardCredit) account.getCard(TypeCard.CREDIT));

        if(cardCredit.isActive()){
            if(cardCredit.getLimit() >= value){
                cardCredit.setLimit(cardCredit.getLimit() - value);
                cardCredit.addPurchase(value);
                try {
                    account.editCardCredit(cardCredit);
                    iAccountService.saveAccount(account);
                } catch (Exception e){
                    throw new Exception(e.getMessage());
                }
            }
            throw new Exception("No credit card limit");
        }
        throw new Exception("Credit card not activated");
    }

    public void payInvoice() throws Exception {
        var account = iAccountService.findAccount();
        var cardCredit = ((CardCredit) account.getCard(TypeCard.CREDIT));

        if(cardCredit != null){
            try {
                cardCredit.deletePurchases();
                account.editCardCredit(cardCredit);
                iAccountService.saveAccount(account);
            } catch (Exception e){
                throw new Exception(e.getMessage());
            }
        }
        throw new Exception("Non-existent credit card");
    }
}
