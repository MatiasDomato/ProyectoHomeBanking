package com.mindhub.homebanking.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String name;
    private Double maxAmount;
    private Double interes;

    @ElementCollection
    @Column(name="payment_id")
    private List<Integer> payments = new ArrayList<>();

    @OneToMany(mappedBy="loan", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();


    public Loan() {
    }

    public Loan(String name, Double maxAmount, List<Integer> payments,Double interes) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.interes = interes;
    }

    public long getId() {return id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public Double getMaxAmount() {return maxAmount;}
    public void setMaxAmount(Double maxAmount) {this.maxAmount = maxAmount;}

    public List<Integer> getPayments() {return payments;}
    public void setPayments(List<Integer> payments) {this.payments = payments;}

    public Set<ClientLoan> getClientLoans() {return clientLoans;}
    public void setClientLoans(Set<ClientLoan> clientLoans) {this.clientLoans = clientLoans;}

    public Double getInteres() {return interes;}
    public void setInteres(Double interes) {this.interes = interes;}
}
