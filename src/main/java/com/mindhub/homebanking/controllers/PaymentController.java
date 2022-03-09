package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.dtos.PaymentsDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class PaymentController {
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

        @Transactional
        @PostMapping("/payments")
        public ResponseEntity<Object> paymentDto(@RequestBody PaymentsDTO paymentsDTO, Authentication authentication) {

            Client client = clientRepository.findByEmail(authentication.getName());

            Account account = accountRepository.findByNumber(paymentsDTO.getNumber());


            if (paymentsDTO == null) {
                return new ResponseEntity<>("Los datos estan incompletos", HttpStatus.FORBIDDEN);
            }

//            if (paymentsDTO.getAmount() < 0 || paymentsDTO.getNumber().isEmpty() || paymentsDTO.getDescription().isEmpty() || paymentsDTO.getCvv() != card.getCvv()) {
//                return new ResponseEntity<>("Los parametros estan vacios", HttpStatus.FORBIDDEN);
//            }

            if (!client.getFirstName().contains(paymentsDTO.getName())) {
                return new ResponseEntity<>("El nombre no existe", HttpStatus.FORBIDDEN);
            }

//            if (paymentsDTO.getThruDate() == card.getThruDate().toString().substring(0, 10)) {
//                return new ResponseEntity<>("El prestamo no existe", HttpStatus.FORBIDDEN);
//            }

            if (account == null) {
                return new ResponseEntity<>("La cuenta no existe", HttpStatus.FORBIDDEN);
            }

            if (account.getBalance() < paymentsDTO.getAmount()) {
                return new ResponseEntity<>("El monto supera el maximo del prestamo", HttpStatus.FORBIDDEN);
            }



            return new ResponseEntity<>("Se creo con exito",HttpStatus.CREATED);
        }
    }
}
