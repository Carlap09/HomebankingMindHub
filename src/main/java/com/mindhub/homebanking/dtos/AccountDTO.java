package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.Account;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;


public class AccountDTO {
        private Long id;
        private String number;
        private LocalDate creationDate;
        private double balance;

    private Set<TransactionDTO> transactions;

    public AccountDTO() {
    }

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate= account.getCreationDate();
        this.balance= account.getBalance();
        this.transactions= account.getTransactions().stream()
                .map(TransactionDTO::new)
                .collect(Collectors.toSet());
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
    public void setTransactions(Set<TransactionDTO> transaction) {
        this.transactions = transaction;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }
}
