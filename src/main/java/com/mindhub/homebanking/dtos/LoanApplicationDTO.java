package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {
    private String loanType;
    private Double amount;
    private int payment;
    private String  numberAccount;


    public LoanApplicationDTO() {
    }

    public LoanApplicationDTO(String loanType, Double amount, int payment, String numberAccount) {
        this.loanType = loanType;
        this.amount = amount;
        this.payment = payment;
        this.numberAccount = numberAccount;
    }

    public String getLoanType() {return loanType;}
    public void setLoanType(String loanType) {this.loanType = loanType;}

    public Double getAmount() {return amount;}
    public void setAmount(Double amount) {this.amount = amount;}

    public int getPayment() {return payment;}
    public void setPayment(int payment) {this.payment = payment;}

    public String getNumberAccount() {return numberAccount;}
    public void setNumberAccount(String numberAccount) {this.numberAccount = numberAccount;}
}
