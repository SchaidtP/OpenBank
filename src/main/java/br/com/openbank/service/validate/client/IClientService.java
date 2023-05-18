package br.com.openbank.service.validate.client;

import br.com.openbank.service.validate.client.request.ClientCreateRequest;

public interface IClientService {
    public String createClient(ClientCreateRequest clientCreateRequest) throws Exception;
}
