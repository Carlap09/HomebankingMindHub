package com.mindhub.homebanking.dtos;


import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {
        private Long id;
        private Long loan_Id;
        private String loanName;
        private Double amount;
        private Integer payments;

    public ClientLoanDTO() {
    }

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.loan_Id = clientLoan.getLoan().getId();
        this.loanName = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
    }

    public Long getId() {
        return id;
    }

    public Long getLoan_Id() {
        return loan_Id;
    }

    public String getLoanName() {
        return loanName;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }
}

