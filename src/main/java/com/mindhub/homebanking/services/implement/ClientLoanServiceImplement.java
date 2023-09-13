package com.mindhub.homebanking.services.implement;


import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.services.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClientLoanServiceImplement implements ClientLoanService {
@Autowired
ClientLoanRepository clientLoanRepository;


    @Override
    public List<ClientLoan> getClientLoanByEmailAndLoanName(String email, String loanName) {
        return clientLoanRepository.findByClientEmailAndLoanName(email,loanName);
    }


}
