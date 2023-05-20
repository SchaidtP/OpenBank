package br.com.openbank.model.entity;

import br.com.openbank.model.embedded.Address;
import br.com.openbank.model.enums.TypeClient;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter @Setter
public class Client {

    private UUID id;
    private String cpf;
    private String name;
    private TypeClient typeClient;
    private LocalDate dateOfBirth;
    private Address address;
    private String password;

    public Client(String cpf, String name, LocalDate dateOfBirth, Address address){
        this.id = UUID.randomUUID();
        this.cpf = cpf;
        this.name = name;
        this.typeClient = TypeClient.COMMON;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }
}
