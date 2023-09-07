package com.mindhub.homebanking.dtos;

import java.util.List;

public class LoanApplicationDTO {
        private Long loanTypeId;
        private double amount;
        private List<Integer> payments;
        private String accountToNumber;

    public LoanApplicationDTO() {
    }

    public LoanApplicationDTO(Long loanTypeId, double amount, List<Integer> payments, String accountToNumber) {
        this.loanTypeId = loanTypeId;
        this.amount = amount;
        this.payments = payments;
        this.accountToNumber = accountToNumber;
    }

    public Long getLoanTypeId() {
        return loanTypeId;
    }

    public double getAmount() {
        return amount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public String getAccountToNumber() {
        return accountToNumber;
    }
}