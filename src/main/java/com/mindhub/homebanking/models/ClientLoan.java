package com.mindhub.homebanking.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private Double amount;
    private Integer payments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    public ClientLoan() {
    }

    public ClientLoan(Double amount, Integer payments, Client loanClient, Loan loan) {
        this.amount = amount;
        this.payments = payments;
        this.client = loanClient;
        this.loan = loan;
    }

    public long getId() {return id;}

    public Double getAmount() {return amount;}
    public void setAmount(Double amount) {this.amount = amount;}

    public Integer getPayments() {return payments;}
    public void setPayments(Integer payments) {this.payments = payments;}

    public Client getLoanClient() {return client;}
    public void setLoanClient(Client loanClient) {this.client = loanClient;}

    public Loan getLoan() {return loan;}
    public void setLoan(Loan loan) {this.loan = loan;
    }

}
