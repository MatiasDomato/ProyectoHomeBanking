package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @GetMapping("/loans")
    public Set<LoanDTO> getLoans(){
        Set<LoanDTO>loans = loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toSet());
        return loans;
    }

    @PostMapping("/create/loans")
    public ResponseEntity<String> addLoanAdmin(@RequestBody LoanDTO loanDTO){
        if (loanDTO.getName().isEmpty() || loanDTO.getAmount().isNaN() || loanDTO.getPayments().isEmpty() || loanDTO.getInteres().isNaN()) {
            return new ResponseEntity<>("Los parametros estan vacios", HttpStatus.FORBIDDEN);
        }

        Loan loan = new Loan(loanDTO.getName(),loanDTO.getAmount(),loanDTO.getPayments(),loanDTO.getInteres());
        loanRepository.save(loan);

        return new ResponseEntity<>("Se creo con exito",HttpStatus.CREATED);
    };


    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> addLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {

        Client client = clientRepository.findByEmail(authentication.getName());
        Loan loan = loanRepository.findByName(loanApplicationDTO.getLoanType());
        Account account = accountRepository.findByNumber(loanApplicationDTO.getNumberAccount());


        if (loanApplicationDTO == null) {
            return new ResponseEntity<>("Los datos estan incompletos", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getAmount() == 0 || loanApplicationDTO.getNumberAccount().isEmpty() || loanApplicationDTO.getPayment() == 0) {
            return new ResponseEntity<>("Los parametros estan vacios", HttpStatus.FORBIDDEN);
        }

        if (loan == null) {
            return new ResponseEntity<>("El prestamo no existe", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getAmount() > loan.getMaxAmount()) {
            return new ResponseEntity<>("El monto supera el maximo del prestamo", HttpStatus.FORBIDDEN);
        }

        if (!loan.getPayments().contains(loanApplicationDTO.getPayment())) {
            return new ResponseEntity<>("La cantidad de cuotas no se encuentra disponible", HttpStatus.FORBIDDEN);
        }

        if (account == null) {
            return new ResponseEntity<>("La cuenta no existe", HttpStatus.FORBIDDEN);
        }

        if (!client.getAccounts().contains(account)) {
            return new ResponseEntity<>("La cuenta de destino no pertenece al cliente", HttpStatus.FORBIDDEN);
        }


       Double loanInterest = (loanApplicationDTO.getAmount() * 0.20) + loanApplicationDTO.getAmount();
        Double loanPayments = Math.floor(loanInterest / loanApplicationDTO.getPayment());


        ClientLoan clientLoan1 = new ClientLoan(loanApplicationDTO.getAmount(), loanApplicationDTO.getPayment(), client, loan);
        clientLoanRepository.save(clientLoan1);

        Transaction transaction1 = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(), "Tipo: " + loanApplicationDTO.getLoanType() + ", El Prestamo fue aprobado", LocalDateTime.now(),account);
        transactionRepository.save(transaction1);

        Double balance = account.getBalance() + loanApplicationDTO.getAmount() ;
        account.setBalance(balance);


        return new ResponseEntity<>("Se creo con exito",HttpStatus.CREATED);
    }
}
