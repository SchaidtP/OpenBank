package br.com.openbank.service.pix.response;

import br.com.openbank.model.enums.TypeKeyPix;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class GetPixResponse {
    public UUID idAccount;
    public TypeKeyPix typeKeyPix;
    public String key;
    public boolean active;
}
