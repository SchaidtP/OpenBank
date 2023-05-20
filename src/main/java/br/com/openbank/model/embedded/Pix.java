package br.com.openbank.model.embedded;

import br.com.openbank.model.enums.TypeKeyPix;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class Pix {
    private UUID idAccount;
    private TypeKeyPix typeKeyPix;
    private String key;
    private boolean active;
}
