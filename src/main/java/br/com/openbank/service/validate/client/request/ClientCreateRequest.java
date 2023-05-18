package br.com.openbank.service.validate.client.request;

import lombok.Data;

import java.util.Date;

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
}
