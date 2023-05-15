package br.com.openbank.service.client;

import br.com.openbank.model.embedded.Address;
import br.com.openbank.model.entity.Client;
import br.com.openbank.repository.IClientRepository;
import br.com.openbank.service.client.request.ClientCreateRequest;
import br.com.openbank.service.client.response.ClientEmailResponse;
import br.com.openbank.service.validate.cpf.ICpfValidateService;
import br.com.openbank.service.validate.email.IEmailValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService implements IClientService{

    @Autowired
    private IClientRepository iClientRepository;

    @Autowired
    private ICpfValidateService iCpfValidateService;

    @Autowired
    private IEmailValidateService iEmailValidateService;

    public String createClient(ClientCreateRequest clientCreateRequest) throws Exception {
        if(iClientRepository.findClientByCpf(clientCreateRequest.cpf).isEmpty() && iCpfValidateService.validateCpf(clientCreateRequest.cpf)){
            if(iClientRepository.findClientByEmail(clientCreateRequest.email).isEmpty() && iEmailValidateService.validateEmail(clientCreateRequest.email)){
                var address = new Address(clientCreateRequest.street, clientCreateRequest.number, clientCreateRequest.neighborhood, clientCreateRequest.city, clientCreateRequest.state);
                var client = new Client(clientCreateRequest.cpf, clientCreateRequest.name, clientCreateRequest.dateOfBirth, 0, address, clientCreateRequest.password, clientCreateRequest.phoneNumber, clientCreateRequest.email);
                try {
                    iClientRepository.save(client);
                    //iAccountRepository.createAccount(client.getId());
                    return null;
                } catch (Exception e){
                    throw new Exception(e.getMessage());
                }
            }
            throw new Exception("Invalid email or existing email");
        }
        throw new Exception("Invalid CPF or existing CPF");
    }

    public ClientEmailResponse getClient(String cpf){
        var client = iClientRepository.findClientByCpf(cpf).orElse(null);
        if(client != null){
            return new ClientEmailResponse(client.getCpf(), client.getName(), client.getTypeClient(), client.getAddress(), client.getPhoneNumber(), client.getEmail());
        }
        return null;
    }
}
