package br.com.openbank.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum TypeCard {
    DEBIT(0), CREDIT(1);

    private final Integer id;
    private static final Map<Integer, TypeCard> getTypeCard = new HashMap<>();

    TypeCard(Integer id){
        this.id = id;
    }

    public  Integer getId(){
        return id;
    }

    static {
        for(TypeCard typeCard : TypeCard.values()){
            getTypeCard.put(typeCard.getId(), typeCard);
        }
    }

    public static  TypeCard getTypeCard(Integer id){
        return getTypeCard(id);
    }

}
