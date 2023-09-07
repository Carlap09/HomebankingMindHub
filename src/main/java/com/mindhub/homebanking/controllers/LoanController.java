package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.LoanService;
import com.mindhub.homebanking.services.TransactionsService;
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
    LoanService loanService;

    @Autowired
    TransactionsService transactionsService;

    @Autowired
    AccountService accountService;

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<?> addLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {
        // Verificar que el cliente esté autenticado
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>("El cliente no está autenticado", HttpStatus.UNAUTHORIZED);
        }

        Optional<Client> clientOptional = clientService.getClientEmail(authentication.getName());
        if (!clientOptional.isPresent()) {
            return new ResponseEntity<>("El cliente no está registrado", HttpStatus.UNAUTHORIZED);
        }
        Client client = clientOptional.get();

       // Verificar que el préstamo exista
        if (loanApplicationDTO == null) {
            return new ResponseEntity<>("Ese préstamo no existe", HttpStatus.NOT_FOUND);
        }

        // Verificar que el monto solicitado no exceda el monto máximo del préstamo
        if (loanApplicationDTO.getAmount() > loanApplicationDTO.getAmount()) {
            return new ResponseEntity<>("El monto excede el máximo permitido", HttpStatus.BAD_REQUEST);
        }

        // Verificar que la cantidad de cuotas sea válida
        if (!loanApplicationDTO.getPayments().contains(loanApplicationDTO.getPayments())) {
            return new ResponseEntity<>("La cantidad de cuotas no es válida", HttpStatus.BAD_REQUEST);
        }

        // Verificar que la cuenta de destino exista
        Account account = accountService.getAccByNumber(loanApplicationDTO.getAccountToNumber());
        if (account == null) {
            return new ResponseEntity<>("La cuenta de destino no existe", HttpStatus.NOT_FOUND);
        }

        // Verificar que la cuenta de destino pertenezca al cliente autenticado
        if (!client.getAccounts().contains(account)) {
            return new ResponseEntity<>("La cuenta de destino no pertenece al cliente autenticado", HttpStatus.FORBIDDEN);
        }

        // Crear una solicitud de préstamo con el monto solicitado sumando el 20%
        double totalLoanAmount = loanApplicationDTO.getAmount() * 1.20;

        // Crear una transacción "CREDIT" asociada a la cuenta de destino
        Transactions transactions = new Transactions(account, TransactionsType.CREDIT, totalLoanAmount, loanApplicationDTO.toString() + " loan approved", LocalDateTime.now());
        transactionsService.saveTransactions(transactions);

        // Actualizar la cuenta de destino sumando el monto solicitado
        double newBalance = account.getBalance() + totalLoanAmount;
        account.setBalance(newBalance);
        accountService.saveAcc(account);

        return new ResponseEntity<>("La transacción se ha registrado exitosamente", HttpStatus.CREATED);
    }

    @GetMapping("/loans")
    public List<LoanDTO> getLoans() {
        return this.loanService.getLoans().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @PostMapping("/loan")
    public ResponseEntity<Object> addLoan(@RequestParam String name,
                                          @RequestParam double maxAmount,
                                          @RequestParam List<Integer> payments,
                                          Authentication authentication) {

        // Verificar que el cliente esté autenticado
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>("El cliente no está autenticado", HttpStatus.UNAUTHORIZED);
        }

        Optional<Client> clientOptional = clientService.getClientEmail(authentication.getName());
        if (!clientOptional.isPresent()) {
            return new ResponseEntity<>("El cliente no está registrado", HttpStatus.UNAUTHORIZED);
        }
        Client client = clientOptional.get();

        // Verificar que el cliente tenga permisos adecuados (por ejemplo, un administrador)
        if (!"admin@mindhub.com".equals(client.getEmail())) {
            return new ResponseEntity<>("Usted no tiene permisos para crear un préstamo", HttpStatus.FORBIDDEN);
        }

        // Crear una nueva opción de préstamo
        loanService.saveLoan(new Loan(null, name, maxAmount, payments));

        return new ResponseEntity<>("Se ha creado una nueva opción de préstamo", HttpStatus.ACCEPTED);
    }
}

