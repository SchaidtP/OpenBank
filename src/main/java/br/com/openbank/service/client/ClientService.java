package br.com.openbank.service.client;

import br.com.openbank.model.embedded.Address;
import br.com.openbank.model.entity.Client;
import br.com.openbank.repository.IClientRepository;
import br.com.openbank.service.client.request.ClientCreateRequest;
import br.com.openbank.service.account.IAccountService;
import br.com.openbank.service.client.request.ClientEditRequest;
import br.com.openbank.service.validate.cpf.ICpfValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class ClientService implements IClientService{

    @Autowired
    IClientRepository iClientRepository;

    @Autowired
    ICpfValidateService iCpfValidateService;

    @Autowired
    IAccountService iAccountService;

    public void createClient(ClientCreateRequest clientCreateRequest) throws Exception{
        if(iClientRepository.findClientByCpf(clientCreateRequest.cpf).isEmpty() && iCpfValidateService.validateCpf(clientCreateRequest.cpf)){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dateOfBirth = LocalDate.parse(clientCreateRequest.dateOfBirth, formatter);
            if(isOlderThan16(dateOfBirth)){
                Address address = new Address(clientCreateRequest.street, clientCreateRequest.number, clientCreateRequest.zipCode, clientCreateRequest.neighborhood, clientCreateRequest.city, clientCreateRequest.state);
                Client client = new Client(clientCreateRequest.cpf, clientCreateRequest.name, dateOfBirth, address);
                try {
                    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                    client.setPassword(encoder.encode(clientCreateRequest.password));
                    iClientRepository.save(client);
                    iAccountService.createAccount(client.getId(), clientCreateRequest.typeAccount);
                    return;
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
            }
            throw new Exception("Invalid age");
        }
        throw new Exception("Customer already registered or invalid CPF");
    }

    public void editClient(ClientEditRequest clientEditRequest) {

    }

    public Client findClientById(UUID idClient) {
        return iClientRepository.findById(idClient).orElse(null);
    }

    public Client getClientByCpf(String cpf) {
        return iClientRepository.findClientByCpf(cpf).orElse(null);
    }


    private boolean isOlderThan16(LocalDate dateOfBirth){
        LocalDate currentDate = LocalDate.now();
        Period ageDifference = Period.between(dateOfBirth, currentDate);
        int age = ageDifference.getYears();
        return age > 16;
    }
}
