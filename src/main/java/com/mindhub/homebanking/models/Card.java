package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String cardHolder;
    private CardType type;
    private CardColor color;
    private String number;
    private int cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;
    private boolean active = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cardClient")
    private Client client;



    public Card() {
    }

    public Card(String cardHolder, CardType type, CardColor color, String number, int cvv, LocalDate fromDate, LocalDate thruDate, Client client) {
        this.cardHolder = cardHolder;
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.client = client;
    }


    public long getId() {return id;}

    public String getCardHolder() {return cardHolder;}
    public void setCardHolder(String cardHolder) {this.cardHolder = cardHolder;}

    public CardType getType() {return type;}
    public void setType(CardType type) {this.type = type;}

    public CardColor getColor() {return color;}
    public void setColor(CardColor color) {this.color = color;}

    public String getNumber() {return number;}
    public void setNumber(String number) {this.number = number;}

    public int getCvv() {return cvv;}
    public void setCvv(int cvv) {this.cvv = cvv;}

    public LocalDate getFromDate() {return fromDate;}
    public void setFromDate(LocalDate fromDate) {this.fromDate = fromDate;}

    public LocalDate getThruDate() {return thruDate;}
    public void setThruDate(LocalDate thruDate) {this.thruDate = thruDate;}

    public Client getClient() {return client;}
    public void setClient(Client client) {this.client = client;}

    public boolean isActive() {return active;}
    public void setActive(boolean active) {this.active = active;}
}
