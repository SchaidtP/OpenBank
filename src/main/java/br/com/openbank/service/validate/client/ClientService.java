package br.com.openbank.service.validate.client;

import br.com.openbank.model.embedded.Address;
import br.com.openbank.model.entity.Client;
import br.com.openbank.repository.IClientRepository;
import br.com.openbank.service.validate.client.request.ClientCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
public class ClientService implements IClientService{

    @Autowired
    IClientRepository iClientRepository;

    public String createClient(ClientCreateRequest clientCreateRequest) throws Exception{
        if(iClientRepository.findClientByCpf(clientCreateRequest.cpf).isEmpty()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dateOfBirth = LocalDate.parse(clientCreateRequest.dateOfBirth, formatter);
            if(isOlderThan16(dateOfBirth)){
                Address address = new Address(clientCreateRequest.street, clientCreateRequest.number, clientCreateRequest.zipCode, clientCreateRequest.neighborhood, clientCreateRequest.city, clientCreateRequest.state);
                Client client = new Client(clientCreateRequest.cpf, clientCreateRequest.name, dateOfBirth, address);
                try {
                    iClientRepository.save(client);
                    return null;
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
            }
            throw new Exception("Invalid age");
        }
        throw new Exception("Customer already registered");
    }



    private boolean isOlderThan16(LocalDate dateOfBirth){
        LocalDate currentDate = LocalDate.now();
        Period ageDifference = Period.between(dateOfBirth, currentDate);
        int age = ageDifference.getYears();
        return age > 16;
    }
}
