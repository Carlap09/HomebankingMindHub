package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    ClientService clientService;

    @Autowired
    ClientLoanService clientLoanService;

    @Autowired
    LoanService loanService;

    @Autowired
    TransactionsService transactionsService;

    @Autowired
    AccountService accountService;

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<?> addLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {
        // Verify that the client is authenticated
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>("The client is not authenticated", HttpStatus.UNAUTHORIZED);
        }
        // Verify that the data is not empty or that the amount and fees are not 0
        if (loanApplicationDTO.getAmount() <= 0 || loanApplicationDTO.getPayments() <= 0) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    "Invalid data: the fields cannot be empty and the amount " +
                            "and fees cannot be less than or equal to zero");
        }
        // Check that the loan exists
        //Optional<Loan> optionalLoan = loanService.getOptionalLoanById(loanApplicationDTO.getLoanId());
        Loan loan = loanService.getLoan(loanApplicationDTO.getLoanId());
        if (loan == null) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The loan does not exist");

        }

        // Verify that the loan exists

        // Verify that the requested amount does not exceed the maximum loan amount
        if (loanApplicationDTO.getAmount() > loan.getMaxAmount()) {
            return new ResponseEntity<>("The amount exceeds the maximum allowed", HttpStatus.BAD_REQUEST);
        }

        // Verify that the number of installments is valid
        if (!loan.getPayments().stream().anyMatch(payments->payments.equals(loanApplicationDTO.getPayments()))) {
            return new ResponseEntity<>("The amount of installments is not valid", HttpStatus.BAD_REQUEST);
        }
        Account account = accountService.getAccByNumber(loanApplicationDTO.getToAccountNumber());
        // Verify that the target account exists

        if (account == null) {
            return new ResponseEntity<>("The destination account does not exist", HttpStatus.NOT_FOUND);
        }


        List <ClientLoan> existingLoans = clientLoanService.getClientLoanByEmailAndLoanName(authentication.getName(),loan.getName());

        if (!existingLoans.isEmpty()) {
            // The client already has a loan with this name, we return an error response
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You already have a loan with this name.");
        }


        Client authenticadedClient = clientService.getClientByEmail(authentication.getName());


        //get authenticated client accounts
        List<Account> authenticatedClientAccounts = accountService.getAccountsByClient(authenticadedClient);


        if (!authenticatedClientAccounts.contains(account)) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This destination account does not belong to the logged in client");

        }

        //add the total amount requested plus 20% interest
        long totalAmount = (long) (loanApplicationDTO.getAmount() * 1.20);

        // Create a "CREDIT" transaction associated with the destination account
        //Account account = accountService.getAccByNumber(loanApplicationDTO.getAccountToNumber());
        Transactions transactions = new Transactions(account, TransactionsType.CREDIT,totalAmount, loanApplicationDTO.toString() + " loan approved", LocalDateTime.now());
        transactionsService.saveTransactions(transactions);
        // Update the destination account by adding the requested amount
        double newBalance = account.getBalance() + totalAmount;
        account.setBalance(newBalance);
        accountService.saveAcc(account);




        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully requested loan");
    }

    @GetMapping("/loans")
    public List<LoanDTO> getLoan() {
        return loanService.getLoan();
    }


}

