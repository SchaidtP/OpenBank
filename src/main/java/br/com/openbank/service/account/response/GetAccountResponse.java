package br.com.openbank.service.account.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class GetAccountResponse {
    public UUID idAccount;
    public UUID idClient;
    public Double balance;
    public LocalDate dateAccount;
}
