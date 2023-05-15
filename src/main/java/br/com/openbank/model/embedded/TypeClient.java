package br.com.openbank.model.embedded;

import java.util.HashMap;
import java.util.Map;

public enum TypeClient {
    COMUM(0), SUPER(1), PREMIUM(2);

    private final Integer code;
    private static final Map<Integer, TypeClient> getTypeClient = new HashMap<>();

    TypeClient(Integer code){
        this.code = code;
    }

    public Integer getCode(){
        return code;
    }

    static {
        for(TypeClient typeClient: TypeClient.values()){
            getTypeClient.put(typeClient.getCode(), typeClient);
        }
    }

    public static TypeClient getTypeClient(Integer code){
        return getTypeClient.get(code);
    }
}
