package br.com.openbank.controller;

import br.com.openbank.service.account.IAccountService;
import br.com.openbank.service.account.request.AccountTransferMoneyRequest;
import br.com.openbank.service.account.response.GetAccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    @Autowired
    IAccountService iAccountService;

    @PatchMapping("/deposit")
    public ResponseEntity<String> deposit(Double balance){
        try {
            iAccountService.deposit(balance);
            return ResponseEntity.status(HttpStatus.OK).body("Deposit made successfully");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestBody AccountTransferMoneyRequest accountTransferMoneyRequest){
        try {
            iAccountService.transferMoney(accountTransferMoneyRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Money transferred successfully");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<GetAccountResponse> getAccount(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(iAccountService.getAccount());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
