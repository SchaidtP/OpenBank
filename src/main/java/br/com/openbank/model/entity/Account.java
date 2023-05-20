package br.com.openbank.model.entity;

import br.com.openbank.model.embedded.Card;
import br.com.openbank.model.embedded.CardCredit;
import br.com.openbank.model.embedded.Pix;
import br.com.openbank.model.enums.TypeAccount;
import br.com.openbank.model.enums.TypeCard;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class Account {
    private UUID id;
    private UUID idClient;
    private Double balance;
    private Pix pix;
    private TypeAccount typeAccount;
    private LocalDate dateAccount;
    private List<Card> cards;

    public Account(UUID idClient, TypeAccount typeAccount, LocalDate dateAccount){
        this.id = UUID.randomUUID();
        this.idClient = idClient;
        this.balance = 0.0;
        this.pix = null;
        this.typeAccount = typeAccount;
        this.dateAccount = dateAccount;
        this.cards = new ArrayList<>();
    }

    public Card getCard(TypeCard typeCard){
        for (Card card : this.cards){
            if(card.getTypeCard().equals(typeCard)){
                return card;
            }
        }
        return null;
    }

    public void addCard(Card card){
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
