package com.mindhub.homebanking.services;


import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import java.util.List;
import java.util.Optional;


public interface LoanService {

    void saveLoan(Loan loan);

    Loan getLoan(long id);

    List<LoanDTO> getLoan();

    Optional<Loan> getOptionalLoanById(Long Id);
}
