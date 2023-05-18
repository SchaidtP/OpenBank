package br.com.openbank.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum TypeAccount {
    CHAIN(0), SAVINGS(1);

    private final Integer id;
    private static final Map<Integer, TypeAccount> getTypeAccount = new HashMap<>();

    TypeAccount(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    static {
        for(TypeAccount typeAccount : TypeAccount.values()){
            getTypeAccount.put(typeAccount.getId(), typeAccount);
        }
    }

    public static TypeAccount getTypeAccount(Integer id){
        return getTypeAccount.get(id);
    }
}
