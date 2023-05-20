package br.com.openbank.service.account.request;

import lombok.Data;

@Data
public class AccountTransferMoneyRequest {
    public String recipientCpf;
    public Double value;
}
