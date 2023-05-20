package br.com.openbank.service.client;

import br.com.openbank.model.entity.Client;
import br.com.openbank.service.client.request.ClientCreateRequest;
import br.com.openbank.service.client.request.ClientEditRequest;

import java.util.UUID;

public interface IClientService {
    void createClient(ClientCreateRequest clientCreateRequest) throws Exception;

    void editClient(ClientEditRequest clientEditRequest);

    Client findClientById(UUID idClient);

    Client getClientByCpf(String cpf);
}
