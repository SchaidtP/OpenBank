package br.com.openbank.service.card.debit;

import br.com.openbank.service.card.debit.response.GetCardDebitResponse;

public interface ICardDebitService {
    void createCardDebit(Double limit) throws Exception;
    void deleteCardDebit() throws Exception;
    GetCardDebitResponse getCardDebit() throws Exception;
    String activateAndDeactivateCardDebit() throws Exception;
    void buyCardDebit(Double value) throws Exception;
}
