package br.com.openbank.service.client.request;

import lombok.Data;

@Data
public class ClientCreateRequest {
    public String cpf;
    public String name;
    public String dateOfBirth;
    public String street;
    public Integer number;
    public String zipCode;
    public String neighborhood;
    public String city;
    public String state;
    public Integer typeAccount;
    public String password;
}