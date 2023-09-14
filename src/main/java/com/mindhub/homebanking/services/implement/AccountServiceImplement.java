package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AccountServiceImplement implements AccountService {

    @Autowired
    AccountRepository accountRepository;
    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public void save(Account newAccount) {
        accountRepository.save(newAccount);
    }


    @Override
    public Account findByNumber(String fromAccountNumber) {
        return accountRepository.findByNumber(fromAccountNumber);
    }

    @Override
    public Account getAccByNumber(String accountDestiny) {
        return accountRepository.findByNumber(accountDestiny);
    }

    @Override
    public void saveAcc(Account account) {
        accountRepository.save(account);

    }

    @Override
    public Account getOptionalAccountByNumber(String accountToNumber) {
        return accountRepository.findByNumber(accountToNumber);
    }

    @Override
    public List<Account> getAccountsByClient(Client client) {
        return accountRepository.findByClient(client);
    }


}