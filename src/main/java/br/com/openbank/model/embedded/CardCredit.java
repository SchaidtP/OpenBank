package br.com.openbank.model.embedded;

import br.com.openbank.model.enums.TypeCard;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Getter @Setter
public class CardCredit extends Card{
    private Double limit;
    private List<Purchase> purchases;
    private Date dateDue;
    private Double invoiceAmount;

    public CardCredit(Double limit){
        super(TypeCard.CREDIT);
        this.limit = limit;
        this.purchases = new ArrayList<>();
        this.dateDue = this.getDateAdd1Month();
        this.invoiceAmount = 0.0;
    }

    public Date getDateAdd1Month(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    public void addPurchase(Double value){
        this.purchases.add(new Purchase(LocalDate.now(), value));
    }

    public void deletePurchases(){
        for (Purchase purchase : this.purchases){
            this.limit += purchase.getValue();
        }
        this.purchases = new ArrayList<>();
    }
}
