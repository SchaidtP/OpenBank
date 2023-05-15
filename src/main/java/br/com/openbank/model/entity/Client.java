package br.com.openbank.model.entity;

import br.com.openbank.model.embedded.Address;
import br.com.openbank.model.embedded.TypeClient;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Client {
    private UUID id;
    private String cpf;
    private String name;
    private Date dateOfBirth;
    private TypeClient typeClient;
    private Address address;
    private String password;
    private Long phoneNumber;
    private String email;

    public Client(String cpf, String name, Date dateOfBirth, Integer typeClient, Address address, String password, Long phoneNumber, String email){
        this.id = UUID.randomUUID();
        this.cpf = cpf;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.typeClient = TypeClient.getTypeClient(typeClient);
        this.address = address;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
