package br.com.openbank.service.card.credit.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetPurchase {
    public LocalDate date;
    public Double value;
}
