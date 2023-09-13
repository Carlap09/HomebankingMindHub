package com.mindhub.homebanking.services;


import com.mindhub.homebanking.models.ClientLoan;


import java.util.List;

public interface ClientLoanService {

    List<ClientLoan> getClientLoanByEmailAndLoanName(String email, String loanName);
}