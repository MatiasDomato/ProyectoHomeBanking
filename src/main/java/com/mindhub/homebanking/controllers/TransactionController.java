package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("clients/current/accounts")
    public Set<AccountDTO> getAll (Authentication authentication){
        ClientDTO client = new ClientDTO(clientRepository.findByEmail(authentication.getName()));
        Set<AccountDTO> AccountsDto = client.getAccounts();
        return AccountsDto;
    }

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction(Authentication authentication,
                                                @RequestParam Double amount, @RequestParam String description,
                                                @RequestParam String sourceAccount, @RequestParam String destinationAccountNumber) {

        Account sourceAccounts = accountRepository.findByNumber(sourceAccount);
        Account destinationAccountNumbers = accountRepository.findByNumber(destinationAccountNumber);

        Client client = clientRepository.findByEmail(authentication.getName());
        List<Account> clientAccount = client.getAccounts().stream().collect(Collectors.toList());


        if (amount == null || description == null || sourceAccount == null || destinationAccountNumber == null) {
            return new ResponseEntity<>("Los parametros estan vacios", HttpStatus.FORBIDDEN);
        }
        if (sourceAccount == destinationAccountNumber){
            return new ResponseEntity<>("Los numeros de cuenta son iguales", HttpStatus.FORBIDDEN);
        }

        if (sourceAccounts == null || destinationAccountNumbers == null){
            return new ResponseEntity<>("No existe la cuenta de origen", HttpStatus.FORBIDDEN);
        }

        if (!clientAccount.contains(sourceAccounts)){
            return new ResponseEntity<>("No tiene permiso en la cuenta", HttpStatus.FORBIDDEN);
        }

        if (destinationAccountNumbers == null){
            return new ResponseEntity<>("No existe la cuenta a la que quieres enviar la transaccion", HttpStatus.FORBIDDEN);
        }

        if (sourceAccounts.getBalance() < amount){
            return new ResponseEntity<>("No tiene saldo suficiente", HttpStatus.FORBIDDEN);
        }


        Transaction transaction1 = new Transaction(TransactionType.DEBIT,amount,destinationAccountNumber+" "+description,LocalDateTime.now(),sourceAccounts);
        transactionRepository.save(transaction1);

        Transaction transaction2 = new Transaction(TransactionType.CREDIT,amount,sourceAccount+" "+description,LocalDateTime.now(),destinationAccountNumbers);
        transactionRepository.save(transaction2);

        Double auxSourceAccount = sourceAccounts.getBalance() - amount;
        Double auxdestinationAccountNumbers = destinationAccountNumbers.getBalance() + amount;

        sourceAccounts.setBalance(auxSourceAccount);
        destinationAccountNumbers.setBalance(auxdestinationAccountNumbers);

        return new ResponseEntity<>("Se creo con exito",HttpStatus.CREATED);
    }



}
