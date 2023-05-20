package br.com.openbank.controller;

import br.com.openbank.service.client.IClientService;
import br.com.openbank.service.client.request.ClientCreateRequest;
import br.com.openbank.service.client.request.ClientEditRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/client")
public class ClientController {

    @Autowired
    IClientService iClientService;

    @PostMapping()
    public ResponseEntity<String> createClient(@RequestBody ClientCreateRequest clientCreateRequest){
        try{
            iClientService.createClient(clientCreateRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Client created successfully");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    public ResponseEntity<String> editClient(@RequestBody ClientEditRequest clientEditRequest){
        try{
            iClientService.editClient(clientEditRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Client created successfully");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}
