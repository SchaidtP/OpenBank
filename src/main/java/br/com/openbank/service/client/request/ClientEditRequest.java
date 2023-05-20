package br.com.openbank.service.client.request;

import lombok.Data;

@Data
public class ClientEditRequest {
    public String name;
    public String street;
    public Integer number;
    public String zipCode;
    public String neighborhood;
    public String city;
    public String state;
    public String password;
    public String email;
    public String telephone;
}
