package br.com.openbank.service.client;

import br.com.openbank.model.entity.Client;
import br.com.openbank.service.client.request.ClientCreateRequest;
import br.com.openbank.service.client.request.ClientEditRequest;
import br.com.openbank.service.client.response.GetClientResponse;

import java.util.UUID;

public interface IClientService {
    void createClient(ClientCreateRequest clientCreateRequest) throws Exception;
    void editClient(ClientEditRequest clientEditRequest) throws Exception;
    Client findClientById(UUID idClient);
    Client getClientByCpf(String cpf);
    GetClientResponse getClient();
    void deleteClient() throws Exception;
    void setTypeClient(Double balance) throws Exception;
}
