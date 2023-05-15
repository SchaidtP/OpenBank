package br.com.openbank.service.client.response;

import br.com.openbank.model.embedded.Address;
import br.com.openbank.model.embedded.TypeClient;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientEmailResponse {
    public String cpf;
    public String name;
    public TypeClient typeClient;
    public Address address;
    public Long phoneNumber;
    public String email;
}
