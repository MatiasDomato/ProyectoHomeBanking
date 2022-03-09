package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.CardsDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CardRepository cardRepository;

    @GetMapping("/clients/current/cards")
    public Set<CardsDTO> getCards(){
        Set<CardsDTO> cards = cardRepository.findAll().stream().map(card -> new CardsDTO(card)).collect(Collectors.toSet());
        return cards;
    }


    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCards(Authentication authentication,
                                              @RequestParam CardType cardType,
                                              @RequestParam CardColor cardColor) {

        Client clientCurrent = clientRepository.findByEmail(authentication.getName());

       List<Card> listCard = clientCurrent.getCards().stream().filter(card -> {
            return card.getType() == cardType;
        }).collect(Collectors.toList());

        List<Card> listCardActive = listCard.stream().filter(card -> {
            return card.isActive();
        }).collect(Collectors.toList());

       if (listCardActive.size() == 3){
           return new ResponseEntity<>("403 prohibido, Se puede maximo 3 tarjetas por tipo", HttpStatus.FORBIDDEN);
       }

        String cardNumber = CardUtils.getCardNumber();

        int cvv = CardUtils.getCvv();


        LocalDate fromDate = LocalDate.now();
        LocalDate thruDate = fromDate.plusYears(5);

        Card Card1 = new Card(clientCurrent.getFirstName()+" "+clientCurrent.getLastName(), cardType, cardColor, cardNumber, cvv, fromDate, thruDate,clientCurrent);
        cardRepository.save(Card1);
        return new ResponseEntity<>("Se creo la tarjeta con exito",HttpStatus.CREATED);
    }

    @PatchMapping("/clients/current/cards/delete/{id}")
    public ResponseEntity<Object> deleteCard(@PathVariable Long id){
        Card card = cardRepository.findById(id).orElse(null);
        card.setActive(false);
        cardRepository.save(card);
        return new ResponseEntity<>("la tarjeta se elimino con exito",HttpStatus.CREATED);
    }

}
