package br.com.openbank.model.embedded;

import br.com.openbank.model.enums.TypeKeyPix;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Pix {
    private TypeKeyPix typeKeyPix;
    private String key;
    private boolean active;
}
