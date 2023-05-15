package br.com.openbank.model.embedded;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
}
