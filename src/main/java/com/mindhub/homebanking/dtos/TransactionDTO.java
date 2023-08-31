package com.mindhub.homebanking.dtos;


import com.mindhub.homebanking.models.Transactions;
import com.mindhub.homebanking.models.TransactionsType;
import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;
    private TransactionsType type;
    private double amount;
    private String description;
    private LocalDateTime date;
    public TransactionDTO() {
    }

    public TransactionDTO(Transactions transactions) {
        this.id = transactions.getId();
        this.type = transactions.getType();
        this.amount = transactions.getAmount();
        this.description = transactions.getDescription();
        this.date = transactions.getDate();
    }

    public Long getId() {
        return id;
    }

    public TransactionsType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }
}

