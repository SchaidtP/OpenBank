package br.com.openbank.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum TypeClient {
    COMMON(0), SUPER(1), PREMIUM(2);

    private final Integer id;
    private static final Map<Integer, TypeClient> getTypeClient = new HashMap<>();

    TypeClient(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    static {
        for(TypeClient typeClient: TypeClient.values()){
            getTypeClient.put(typeClient.getId(), typeClient);
        }
    }

    public static TypeClient getTypeClient(Integer id){
        return getTypeClient.get(id);
    }
}
