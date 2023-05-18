package br.com.openbank.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum TypeKeyPix {
    CPF(0), EMAIL(1), TELEPHONE(2), RANDOM(3);

    private final Integer id;
    private static final Map<Integer, TypeKeyPix> getTypeKeyPix = new HashMap<>();

    TypeKeyPix(Integer id) {
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    static {
        for(TypeKeyPix typeKeyPix : TypeKeyPix.values()){
            getTypeKeyPix.put(typeKeyPix.getId(), typeKeyPix);
        }
    }

    public static TypeKeyPix getTypeKeyPix(Integer id){
        return getTypeKeyPix.get(id);
    }
}
