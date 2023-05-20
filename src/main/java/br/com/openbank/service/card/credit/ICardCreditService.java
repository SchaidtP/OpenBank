package br.com.openbank.service.card.credit;

import br.com.openbank.service.card.credit.response.GetCardCreditResponse;

public interface ICardCreditService {
    void createCardCredit() throws Exception;
    void deleteCardCredit() throws Exception;
    GetCardCreditResponse getCardCredit() throws Exception;
    String activateAndDeactivateCardCredit() throws Exception;
    void buyCardCredit(Double value) throws Exception;
    void payInvoice() throws Exception;
}
