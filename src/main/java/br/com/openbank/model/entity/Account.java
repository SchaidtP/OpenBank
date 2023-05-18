package br.com.openbank.model.entity;

import br.com.openbank.model.embedded.Card;
import br.com.openbank.model.embedded.CardCredit;
import br.com.openbank.model.embedded.Pix;
import br.com.openbank.model.enums.TypeAccount;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class Account {
    private UUID idAccount;
    private UUID idClient;
    private Double balance;
    private Pix pix;
    private TypeAccount typeAccount;
    private Date dateAccount;
    private List<Card> cards;

    public void addCard(Card card){
        if(this.cards == null){
            this.cards = new ArrayList<Card>();
        }
        this.cards.add(card);
    }

    public void editCardCredit(CardCredit cardCredit){
        List<Card> lCard = new ArrayList<>();
        for(Card card : this.cards){
            if(card.getClass().getSimpleName().toLowerCase().contains("credit")){
                lCard.add(cardCredit);
            }
        }
        this.cards = lCard;
    }
}
