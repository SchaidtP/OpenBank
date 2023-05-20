package br.com.openbank.service.card.credit.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class GetCardCreditResponse {
    public String number;
    public String flag;
    public boolean active;
    public Double limit;
    public List<GetPurchase> purchases;
    public Date dateDue;
    public Double invoiceAmount;
}
