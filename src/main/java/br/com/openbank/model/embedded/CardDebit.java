package br.com.openbank.model.embedded;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class CardDebit extends  Card{
    private double limitPerTransaction;

    public CardDebit(Double limitPerTransaction){
        super();
        this.limitPerTransaction = limitPerTransaction;
    }
}
