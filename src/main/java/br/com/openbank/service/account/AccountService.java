package br.com.openbank.service.account;

import br.com.openbank.model.embedded.CardCredit;
import br.com.openbank.model.entity.Account;
import br.com.openbank.model.entity.Client;
import br.com.openbank.model.enums.TypeAccount;
import br.com.openbank.model.enums.TypeCard;
import br.com.openbank.repository.IAccountRepository;
import br.com.openbank.service.account.request.AccountTransferMoneyRequest;
import br.com.openbank.service.account.response.GetAccountResponse;
import br.com.openbank.service.client.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService implements IAccountService{

    @Autowired
    IAccountRepository iAccountRepository;

    @Autowired
    IClientService iClientService;

    public void createAccount(UUID idClient, Integer typeAccount) throws Exception {
        Account account = new Account(idClient, TypeAccount.getTypeAccount(typeAccount), dateOneMonthLater());
        try {
            iAccountRepository.save(account);
            return;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void deleteAccount(UUID idClient) throws Exception {
        var account = iAccountRepository.findAccountByIdClient(idClient).orElse(null);

        if (account.getBalance() < 0.0 || account.getBalance() > 0.0){
            throw new Exception("Unable to delete due to balance, please check it");
        }

        var cardCredit = (CardCredit) account.getCard(TypeCard.CREDIT);
        if(cardCredit != null){
            if(cardCredit.getInvoiceAmount() > 0.0) {
                throw new Exception("Impossible to delete customer, because open invoice on credit card");
            }
        }

        try {
            iAccountRepository.delete(account);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void deposit(Double balance) throws Exception {
        var client = ((Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        var account = iAccountRepository.findAccountByIdClient(client.getId()).orElse(null);

        if(balance < 0){
            throw new Exception("Invalid deposit");
        }

        Double value = account.getBalance();
        account.setBalance(balance + value);

        try {
            iAccountRepository.save(account);
            iClientService.setTypeClient(account.getBalance());
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void transferMoney(AccountTransferMoneyRequest accountTransferMoneyRequest) throws Exception {
        Client recipientCpfClient = iClientService.getClientByCpf(accountTransferMoneyRequest.recipientCpf);

        if(recipientCpfClient != null){
            Account recipientCpfAccount = iAccountRepository.findAccountByIdClient(recipientCpfClient.getId()).orElse(null);
            Client senderCpfClient = ((Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            Account senderCpfAccount = iAccountRepository.findAccountByIdClient(senderCpfClient.getId()).orElse(null);

            if(recipientCpfAccount.getTypeAccount() != senderCpfAccount.getTypeAccount()){
                accountTransferMoneyRequest.value += 5.60;
            }

            if(senderCpfAccount.getBalance() < accountTransferMoneyRequest.value){
                throw new Exception("Insufficient funds");
            }

            senderCpfAccount.setBalance(senderCpfAccount.getBalance() - accountTransferMoneyRequest.value);
            recipientCpfAccount.setBalance(recipientCpfAccount.getBalance() + accountTransferMoneyRequest.value);

            try {
                iAccountRepository.save(senderCpfAccount);
                iAccountRepository.save(recipientCpfAccount);
                iClientService.setTypeClient(senderCpfAccount.getBalance());
                iClientService.setTypeClient(recipientCpfAccount.getBalance());
            } catch (Exception e){
                throw new Exception(e.getMessage());
            }

        }
        throw new Exception("Invalid recipient");
    }

    public GetAccountResponse getAccount() {
        var client = ((Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        var account = iAccountRepository.findAccountByIdClient(client.getId()).orElse(null);
        return new GetAccountResponse(account.getId(), account.getIdClient(), account.getBalance(), account.getDateAccount());
    }

    public void maintenanceFee() {
        List<Account> accounts = iAccountRepository.findAll();

        if (accounts != null){
            for (Account account : accounts){
                if (account.getTypeAccount() == TypeAccount.CHAIN){
                    account.setBalance(account.getBalance() - (account.getBalance()*0.45));
                } else {
                    account.setBalance(account.getBalance() - (account.getBalance()*0.03));
                }

                try {
                    account.setDateAccount(dateOneMonthLater());
                    iAccountRepository.save(account);
                } catch (Exception ignored){

                }
            }
        }
    }

    private LocalDate dateOneMonthLater(){
        LocalDate currentDate = LocalDate.now();
        return currentDate.plusMonths(1);
    }

    public Account findAccount(){
        var client = ((Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return iAccountRepository.findAccountByIdClient(client.getId()).orElse(null);
    }

    public void saveAccount(Account account) throws Exception {
        try {
            iAccountRepository.save(account);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<Account> findAll(){
        return iAccountRepository.findAll();
    }
}
