package br.com.openbank.service.client.response;

import br.com.openbank.model.enums.TypeKeyPix;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class GetClientResponse {
    public UUID idClient;
    public String cpf;
    public String name;
    public String typeClient;
    public LocalDate dateOfBirth;
    public String street;
    public Integer number;
    public String zipCode;
    public String neighborhood;
    public String city;
    public String state;
}
