package br.com.openbank.service.client;

import br.com.openbank.model.embedded.Address;
import br.com.openbank.model.entity.Client;
import br.com.openbank.model.enums.TypeClient;
import br.com.openbank.repository.IClientRepository;
import br.com.openbank.service.client.request.ClientCreateRequest;
import br.com.openbank.service.account.IAccountService;
import br.com.openbank.service.client.request.ClientEditRequest;
import br.com.openbank.service.client.response.GetClientResponse;
import br.com.openbank.service.validate.IValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    IValidateService iValidateService;

    @Autowired
    IAccountService iAccountService;

    public void createClient(ClientCreateRequest clientCreateRequest) throws Exception{
        if(iClientRepository.findClientByCpf(clientCreateRequest.cpf).isEmpty() && iValidateService.validateCpf(clientCreateRequest.cpf)){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dateOfBirth = LocalDate.parse(clientCreateRequest.dateOfBirth, formatter);
            if(isOlderThan16(dateOfBirth) && iValidateService.validateEmail(clientCreateRequest.email)){
                Address address = new Address(clientCreateRequest.street, clientCreateRequest.number, clientCreateRequest.zipCode, clientCreateRequest.neighborhood, clientCreateRequest.city, clientCreateRequest.state);
                Client client = new Client(clientCreateRequest.cpf, clientCreateRequest.name, dateOfBirth, address, clientCreateRequest.email, clientCreateRequest.telephone);
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
            throw new Exception("Invalid age or email");
        }
        throw new Exception("Customer already registered or invalid CPF");
    }

    public void editClient(ClientEditRequest clientEditRequest) throws Exception {
        var client = ((Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if(clientEditRequest.name != null && !clientEditRequest.name.equals("")){
            client.setName(clientEditRequest.name);
        }

        if(clientEditRequest.street != null && !clientEditRequest.street.equals("")){
            client.getAddress().setStreet(clientEditRequest.street);
        }

        if(clientEditRequest.number != null && clientEditRequest.number == 0){
            client.getAddress().setNumber(clientEditRequest.number);
        }

        if(clientEditRequest.zipCode != null && !clientEditRequest.zipCode.equals("")){
            client.getAddress().setZipCode(clientEditRequest.zipCode);
        }

        if(clientEditRequest.neighborhood != null && !clientEditRequest.neighborhood.equals("")){
            client.getAddress().setNeighborhood(clientEditRequest.neighborhood);
        }

        if(clientEditRequest.city != null && !clientEditRequest.city.equals("")){
            client.getAddress().setCity(clientEditRequest.city);
        }

        if(clientEditRequest.state != null && !clientEditRequest.state.equals("")){
            client.getAddress().setState(clientEditRequest.state);
        }

        if(clientEditRequest.password != null && !clientEditRequest.password.equals("")){
            client.getAddress().setStreet(clientEditRequest.password);
        }

        if(clientEditRequest.email != null && !clientEditRequest.state.equals("")){
            if (iValidateService.validateEmail(clientEditRequest.email)){
                client.setEmail(clientEditRequest.email);
            }
            throw new Exception("Invalid email");
        }

        if(clientEditRequest.telephone != null && !clientEditRequest.telephone.equals("")){
            client.setTelephone(clientEditRequest.telephone);
        }

        try{
            iClientRepository.save(client);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Client findClientById(UUID idClient) {
        return iClientRepository.findById(idClient).orElse(null);
    }

    public Client getClientByCpf(String cpf) {
        return iClientRepository.findClientByCpf(cpf).orElse(null);
    }

    public GetClientResponse getClient() {
        var client = ((Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return new GetClientResponse(client.getId(), client.getCpf(), client.getName(), client.getTypeClient().toString(), client.getDateOfBirth(),
                client.getAddress().getStreet(), client.getAddress().getNumber(), client.getAddress().getZipCode(), client.getAddress().getNeighborhood(), client.getAddress().getCity(), client.getAddress().getState());
    }

    public void deleteClient() throws Exception {
        var client = ((Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        try {
            iAccountService.deleteAccount(client.getId());
            iClientRepository.delete(client);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void setTypeClient(Double balance) throws Exception {
        var client = ((Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(balance <= 5000){
            client.setTypeClient(TypeClient.COMMON);
        } else if(balance > 5000 && balance <= 10000){
            client.setTypeClient(TypeClient.PREMIUM);
        } else {
            client.setTypeClient(TypeClient.SUPER);
        }

        try{
            iClientRepository.save(client);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    private boolean isOlderThan16(LocalDate dateOfBirth){
        LocalDate currentDate = LocalDate.now();
        Period ageDifference = Period.between(dateOfBirth, currentDate);
        int age = ageDifference.getYears();
        return age > 16;
    }


}
