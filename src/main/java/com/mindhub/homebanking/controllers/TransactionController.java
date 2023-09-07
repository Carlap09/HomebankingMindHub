package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transactions;
import com.mindhub.homebanking.models.TransactionsType;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;


    @RestController
    @RequestMapping("/api")
    public class TransactionController{
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionsService transactionsService;
    @Autowired
        ClientService clientService;

    @Transactional
    @RequestMapping(path = "/transactions",method = RequestMethod.POST)
    public ResponseEntity<Object> createTransaction(@RequestParam String fromAccountNumber,
                                                    @RequestParam String toAccountNumber,
                                                    @RequestParam String description,
                                                    @RequestParam Double amount,
                                                    Authentication authentication) {

        Client client = clientService.findByEmail(authentication.getName());
        Account accountFromNumber=accountService.findByNumber(fromAccountNumber);
        Account accountToAcccountNumber =accountService.findByNumber(toAccountNumber);


        // check that the parameters are not empty
        if ((fromAccountNumber == null) || (toAccountNumber == null) || (description == null) || (amount == null)){
            return new ResponseEntity<>("empty parameters 403", HttpStatus.FORBIDDEN);
        }
        if (fromAccountNumber.equals(toAccountNumber)){
            return new ResponseEntity<>("The accounts are the same 403", HttpStatus.FORBIDDEN);
        }
        if (toAccountNumber == null){
            return new ResponseEntity<>("destination account does not exist",HttpStatus.FORBIDDEN);
        }

        Set<Account> setFromAccountNumber = client.getAccounts();
        boolean isAccountAuthorized = setFromAccountNumber.stream().anyMatch(account -> account.getNumber().equals(fromAccountNumber));
        if (!isAccountAuthorized) {
            return new ResponseEntity<>("403 UNAUTHENTICATED ACCOUNT", HttpStatus.FORBIDDEN);
        }


        if (accountToAcccountNumber.getBalance() < amount || amount <= 0){
            return new ResponseEntity<>("You do not have enough balance to make the transaction",HttpStatus.FORBIDDEN);
        }


        // Create and save transactions
        Transactions transactionsFrom = new Transactions(accountFromNumber, TransactionsType.DEBIT, -amount,
                accountFromNumber.getNumber() + " - " + description, LocalDateTime.now());
        Transactions transactionsTo = new Transactions(accountToAcccountNumber, TransactionsType.CREDIT, amount,
                accountToAcccountNumber.getNumber() + " - " + description, LocalDateTime.now());

        transactionsService.save(transactionsFrom);
        transactionsService.save(transactionsTo);

        // Update account balances
        Double updatedBalanceFrom = accountFromNumber.getBalance() - amount;
        Double updatedBalanceTo = accountToAcccountNumber.getBalance() + amount;

        accountFromNumber.setBalance(updatedBalanceFrom);
        accountToAcccountNumber.setBalance(updatedBalanceTo);



        return new ResponseEntity<>("201 transfer successful", HttpStatus.CREATED);
    }
}





