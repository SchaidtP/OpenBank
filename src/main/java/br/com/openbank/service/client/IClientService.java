package br.com.openbank.service.client;

import br.com.openbank.service.client.request.ClientCreateRequest;
import br.com.openbank.service.client.response.ClientEmailResponse;

public interface IClientService {
    String createClient(ClientCreateRequest clientCreateRequest) throws Exception;
    ClientEmailResponse getClient(String cpf);
}
