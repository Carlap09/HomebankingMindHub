package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Transactions;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionsServiceImplement implements TransactionsService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void save(Transactions transactionsFrom){
        transactionRepository.save(transactionsFrom);

    }

    @Override
    public void saveTransactions(Transactions transactions) {
        transactionRepository.save(transactions);
    }


}
