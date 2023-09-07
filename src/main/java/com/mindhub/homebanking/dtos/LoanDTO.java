package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;

import java.util.List;

public class LoanDTO {
    private Long id;
    private String name;
    private float maxAmount;
    private List<Integer> payments;

    public LoanDTO() {
    }

    public LoanDTO(Loan loan) {
        this.id = id;
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

}
