package br.com.openbank.controller;

import br.com.openbank.service.pix.IPixService;
import br.com.openbank.service.pix.request.PixTransferRequest;
import br.com.openbank.service.pix.response.GetPixResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account/pix")
public class PixController {

    @Autowired
    IPixService iPixService;

    @PostMapping("/{typeKeyPix}")
    public ResponseEntity<String> createPix(@PathVariable("typeKeyPix") Integer typeKeyPix){
        try {
            iPixService.createPix(typeKeyPix);
            return ResponseEntity.status(HttpStatus.CREATED).body("Pix ready to use");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping()
    public ResponseEntity<String> transferByPix(@RequestBody PixTransferRequest pixTransferRequest){
        try {
            iPixService.transferByPix(pixTransferRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Successful pix transfer");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping()
    public ResponseEntity<String> deletePix(){
        try {
            iPixService.deletePix();
            return ResponseEntity.status(HttpStatus.OK).body("Pix deleted successfully");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<GetPixResponse> getPix(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iPixService.getPix());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
