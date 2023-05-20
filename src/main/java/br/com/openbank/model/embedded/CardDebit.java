package br.com.openbank.model.embedded;

import br.com.openbank.model.enums.TypeCard;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class CardDebit extends  Card{
    private double limitPerTransaction;

    public CardDebit(Double limitPerTransaction){
        super(TypeCard.DEBIT);
        this.limitPerTransaction = limitPerTransaction;
    }
}
