package br.com.openbank.service.client.request;

import lombok.Data;

import java.util.Date;

@Data
public class ClientCreateRequest {
    public String cpf;
    public String name;
    public Date dateOfBirth;
    public String street;
    public String number;
    public String neighborhood;
    public String city;
    public String state;
    public String password;
    public Long phoneNumber;
    public String email;
}
