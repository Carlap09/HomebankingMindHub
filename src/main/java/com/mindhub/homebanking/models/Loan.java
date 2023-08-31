package com.mindhub.homebanking.models;


import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private  Long id;
    private  String name;
    private   Double maxAmount;
    @ElementCollection
    private List<Integer> payments;


    public Loan() {
    }

    public Loan(String name, Double maxAmount, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
    Set<ClientLoan> clientLoans = new HashSet<>();

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }


    public void addClientLoan (ClientLoan clientLoan) {
        clientLoan.setLoan(this);
        clientLoans.add(clientLoan);

    }


}
