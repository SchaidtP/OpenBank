package br.com.openbank.repository;

import br.com.openbank.model.entity.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface IClientRepository extends MongoRepository<Client, UUID> {
    Optional<Client> findClientByCpf(String cpf);
}
