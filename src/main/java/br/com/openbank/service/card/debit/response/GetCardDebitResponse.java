package br.com.openbank.service.card.debit.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetCardDebitResponse {
    public String number;
    public String flag;
    public boolean active;
    public double limitPerTransaction;
}
