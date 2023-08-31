package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;


import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccount(){
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @RequestMapping("/accounts/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);

        return optionalAccount.map(account -> ResponseEntity.ok(new AccountDTO(account)))
                .orElse(ResponseEntity.notFound().build());
    }
    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        String email = authentication.getName();

        Client client = clientRepository.findByEmail(email);

        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }

        if (client.getAccounts().size() >= 3) {
            return new ResponseEntity<>("Maximum account limit reached", HttpStatus.FORBIDDEN);
        }

        // Generate a random account number with the format VIN-XXXXXX
        String accountNumber = "VIN-" + (new Random().nextInt(900000) + 100000);

        // Create a new account with a balance of 0
        Account newAccount = new Account(accountNumber, LocalDate.now(), 0.0);
        newAccount.setClient(client);

        accountRepository.save(newAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/clients/current/accounts")
    public ResponseEntity<List<AccountDTO>> getAccounts(Authentication authentication) {
        String email = authentication.getName();
        Client client = clientRepository.findByEmail(email);

        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<AccountDTO> accountDTO = client.getAccounts().stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

}
