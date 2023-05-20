package br.com.openbank.model.embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
public class Purchase {
    private LocalDate date;
    private Double value;
}
