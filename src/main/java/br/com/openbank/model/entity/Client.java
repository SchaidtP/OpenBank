package br.com.openbank.model.entity;

import br.com.openbank.model.embedded.Address;
import br.com.openbank.model.enums.TypeClient;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter @Setter
public class Client {

    private UUID idClient;
    private String cpf;
    private String name;
    private TypeClient typeClient;
    private Date dateOfBirth;
    private Address address;
}
