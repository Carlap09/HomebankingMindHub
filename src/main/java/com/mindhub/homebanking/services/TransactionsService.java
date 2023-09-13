package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transactions;
import com.mindhub.homebanking.models.TransactionsType;

import java.time.LocalDateTime;


public interface TransactionsService {

    void save(Transactions transactionsFrom);


    void saveTransactions(Transactions transactions);
}

