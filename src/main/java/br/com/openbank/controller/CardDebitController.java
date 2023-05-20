package br.com.openbank.controller;

import br.com.openbank.service.card.credit.response.GetCardCreditResponse;
import br.com.openbank.service.card.debit.ICardDebitService;
import br.com.openbank.service.card.debit.response.GetCardDebitResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/card/debit")
public class CardDebitController {

    @Autowired
    ICardDebitService iCardDebitService;

    @PostMapping("/{limit}")
    public ResponseEntity<String> createCardDebit(@PathVariable("limit") Double limit){
        try{
            iCardDebitService.createCardDebit(limit);
            return ResponseEntity.status(HttpStatus.CREATED).body("Debit card successfully created");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteCardDebit(){
        try{
            iCardDebitService.deleteCardDebit();
            return ResponseEntity.status(HttpStatus.OK).body("Debit card deleted successfully");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<GetCardDebitResponse> getCardDebit(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(iCardDebitService.getCardDebit());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping()
    public ResponseEntity<String> activateAndDeactivateCardDebit(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(iCardDebitService.activateAndDeactivateCardDebit());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/purchase/{value}")
    public ResponseEntity<String> buyCardDebit(@PathVariable("value") Double value){
        try{
            iCardDebitService.buyCardDebit(value);
            return ResponseEntity.status(HttpStatus.OK).body("Purchase accepted");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
