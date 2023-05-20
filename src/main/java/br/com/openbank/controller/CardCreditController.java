package br.com.openbank.controller;

import br.com.openbank.service.card.credit.ICardCreditService;
import br.com.openbank.service.card.credit.response.GetCardCreditResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/card/credit")
public class CardCreditController {

    @Autowired
    ICardCreditService iCardCreditService;

    @PostMapping()
    public ResponseEntity<String> createCardCredit(){
        try{
            iCardCreditService.createCardCredit();
            return ResponseEntity.status(HttpStatus.CREATED).body("Credit card successfully created");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteCardCredit(){
        try{
            iCardCreditService.deleteCardCredit();
            return ResponseEntity.status(HttpStatus.OK).body("Credit card deleted successfully");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<GetCardCreditResponse> getCardCredit(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(iCardCreditService.getCardCredit());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping()
    public ResponseEntity<String> activateAndDeactivateCardCredit(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(iCardCreditService.activateAndDeactivateCardCredit());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/purchase/{value}")
    public ResponseEntity<String> buyCardCredit(@PathVariable("value") Double value){
        try{
            iCardCreditService.buyCardCredit(value);
            return ResponseEntity.status(HttpStatus.OK).body("Purchase accepted");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping("/invoice/pay")
    public ResponseEntity<String> payInvoice(){
        try{
            iCardCreditService.payInvoice();
            return ResponseEntity.status(HttpStatus.OK).body("Invoice Paid");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
