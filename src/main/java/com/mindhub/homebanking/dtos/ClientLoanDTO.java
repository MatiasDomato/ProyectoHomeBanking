package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

public class ClientLoanDTO {
    private long id;
    private long loanid;
    private String name;
    private Double amount;
    private Integer payments;

    public ClientLoanDTO() {
    }

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.loanid = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
    }

    public long getId() {return id;}


    public long getLoanid() {return loanid;}
    public void setLoanid(long loanid) {this.loanid = loanid;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public Double getAmount() {return amount;}
    public void setAmount(Double amount) {this.amount = amount;}

    public Integer getPayments() {return payments;}
    public void setPayments(Integer payments) {this.payments = payments;}
}