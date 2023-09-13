package com.mindhub.homebanking.services;


import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    List<Account> findAll();
    Optional<Account> findById(Long id);
    void save(Account newAccount);

    Account findByNumber(String fromAccountNumber);

    Account getAccByNumber(String accountDestiny);

    void saveAcc(Account account);

    Account getOptionalAccountByNumber(String accountToNumber);

    List<Account> getAccountsByClient(Client client);
}


