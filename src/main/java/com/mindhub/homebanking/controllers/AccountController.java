package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/accounts")
    public Set<AccountDTO> getAccounts(){
        Set<AccountDTO>accounts = accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
        return accounts;
    }

    @GetMapping("accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        AccountDTO account = new AccountDTO(accountRepository.findById(id).orElse(null));
        return account;
    }

    int min = 10000000;
    int max = 99999999;

    public int getRandomNumber(int max, int min) {
        return (int) ((Math.random() * ( max - min)) + min);
    }

    public String getStringRandomNumber() {
        int randomNumber = getRandomNumber(min, max);
        return String.valueOf(randomNumber);
    }

    String numberAccount = getStringRandomNumber();
    String vinNumberAccount = "VIN"+numberAccount;

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication,@RequestParam AccountType accountType) {
        Client clientCurrent = clientRepository.findByEmail(authentication.getName());

        List<Account> accountsActive = clientCurrent.getAccounts().stream().filter(account -> account.getActive().equals(true)).collect(Collectors.toList());

        if (accountsActive.size() >= 3){
            return new ResponseEntity<>("403 prohibido, Se puede maximo 3 cuentas", HttpStatus.FORBIDDEN);
        }
        Account account1 = new Account(vinNumberAccount, LocalDateTime.now(),0.0,clientCurrent, accountType);
        accountRepository.save(account1);
        return new ResponseEntity<>("Se creo con exito",HttpStatus.CREATED);
    }

    @PatchMapping("/clients/current/accounts/delete/{id}")
    public ResponseEntity<Object> deletIdAccount (@PathVariable Long id){
        Account account = accountRepository.findById(id).orElse(null);

        if (account.getBalance() > 0){
            return new ResponseEntity<>("403 prohibido, Se tiene que pasar todo el monto antes de borrar la cuenta", HttpStatus.FORBIDDEN);
        }

        account.setActive(false);
        accountRepository.save(account);

        return new ResponseEntity<>("Se elimino con exito",HttpStatus.CREATED);

    }

}
