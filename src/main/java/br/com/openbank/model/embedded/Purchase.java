package br.com.openbank.model.embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@AllArgsConstructor
public class Purchase {
    private Date date;
    private Double value;
}
