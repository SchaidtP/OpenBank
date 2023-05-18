package br.com.openbank.repository;

import br.com.openbank.model.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface IAccountRepository extends MongoRepository<Account, UUID> {
}
