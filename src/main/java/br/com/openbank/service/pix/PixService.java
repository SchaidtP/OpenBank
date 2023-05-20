package br.com.openbank.service.pix;

import br.com.openbank.model.entity.Account;
import br.com.openbank.model.entity.Client;
import br.com.openbank.model.enums.TypeKeyPix;
import br.com.openbank.service.account.IAccountService;
import br.com.openbank.service.client.IClientService;
import br.com.openbank.service.pix.request.PixTransferRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PixService implements IPixService{

    @Autowired
    IAccountService iAccountService;

    @Autowired
    IClientService iClientService;

    public void createPix(Integer typeKeyPix) throws Exception {
        Account account = iAccountService.findAccount();
        Client client = iClientService.findClientById(account.getIdClient());
        if(typeKeyPix == 0){
            account.getPix().setTypeKeyPix(TypeKeyPix.CPF);
            account.getPix().setKey(client.getCpf());
        } else if(typeKeyPix == 1){
            account.getPix().setTypeKeyPix(TypeKeyPix.EMAIL);
            account.getPix().setKey(client.getEmail());
        } else if(typeKeyPix == 2){
            account.getPix().setTypeKeyPix(TypeKeyPix.TELEPHONE);
            account.getPix().setKey(client.getTelephone());
        } else {
            account.getPix().setTypeKeyPix(TypeKeyPix.RANDOM);
            account.getPix().setKey(UUID.randomUUID().toString());

        }
        try {
            account.getPix().setIdAccount(account.getId());
            account.getPix().setActive(true);
            iAccountService.saveAccount(account);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void transferByPix(PixTransferRequest pixTransferRequest) throws Exception {
        List<Account> accounts = iAccountService.findAll();
        for(Account account: accounts){
            if(account.getPix().getTypeKeyPix() == TypeKeyPix.getTypeKeyPix(pixTransferRequest.typeKeyPix) && account.getPix().isActive()){
                if(account.getPix().getKey().equals(pixTransferRequest.keyPix)){
                    Account senderPix = iAccountService.findAccount();
                    if (senderPix.getPix().isActive() && senderPix.getBalance() >= pixTransferRequest.getValue()){
                        senderPix.setBalance(senderPix.getBalance() - pixTransferRequest.value);
                        account.setBalance(account.getBalance() + pixTransferRequest.value);
                        try{
                            iAccountService.saveAccount(account);
                            iAccountService.saveAccount(senderPix);
                        } catch (Exception e){
                            throw new Exception(e.getMessage());
                        }
                    }
                    throw new Exception("Pix not activated or insufficient value");
                }
                throw new Exception("Pix not found");
            }
            throw new Exception("Invalid or inactive pix");
        }

    }
}
