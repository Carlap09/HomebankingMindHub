package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Transactions;


public interface TransactionsService {
    void save(Transactions transactionsFrom);

    void saveTransactions(Transactions transactions);
}
