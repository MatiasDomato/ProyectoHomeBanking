package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Services.ClientService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public List<ClientDTO>getClients(){
        return clientService.getClients();
    }

    @GetMapping("clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        ClientDTO client = new ClientDTO(clientRepository.findById(id).orElse(null));
        return client;
    }

    @GetMapping("/clients/current")
    public ClientDTO getCurrentClientDto (Authentication authentication) {
        ClientDTO clientDTO = new ClientDTO(clientRepository.findByEmail(authentication.getName()));
        return clientDTO;
    }


    int min = 10000000;
    int max = 99999999;

    public int getRandomNumber(int max, int min) {
        return (int) ((Math.random() * ( max - min)) + min);
    }

    public String getStringRandomNumber(){
        int randomNumber = getRandomNumber(min, max);
        return String.valueOf(randomNumber);
    }

    String numberAccount = getStringRandomNumber();
    String vinNumberAccount = "VIN"+numberAccount;

    @PostMapping("/clients")
    public ResponseEntity<Object> registerClient(

            @RequestParam String firstName, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password) {


        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Client client1= new Client(firstName, lastName, email, passwordEncoder.encode(password));
//        clientRepository.save(client1);
        clientService.saveClient(client1);

        Account account1 = new Account(vinNumberAccount, LocalDateTime.now(),0.0,client1, AccountType.AHORRO);
        accountRepository.save(account1);
        return new ResponseEntity<>("Se creo con exito",HttpStatus.CREATED);
    }


}
