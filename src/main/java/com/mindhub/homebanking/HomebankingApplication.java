package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;


@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
		return (args) -> {

			Client melbaClient = new Client("Melba", "Morel", "melba@mindhub.com");
			Client alexandraClient = new Client("Alexandra", "Araujo", "AlexandraA@gmail.com");

			clientRepository.save(melbaClient);
			clientRepository.save(alexandraClient);

			// Create and associate accounts for Melba
			Account melbaAccount1 = new Account("VIN001", LocalDate.now(), 5000);
			Account melbaAccount2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500);

			melbaAccount1.setClient(melbaClient);
			melbaAccount2.setClient(melbaClient);

			accountRepository.save(melbaAccount1);
			accountRepository.save(melbaAccount2);

			// Create and associate accounts for Alexandra
			Account alexandraAccount1 = new Account("VIN003",LocalDate.now().plusDays(2) , 3000);
			Account alexandraAccount2 = new Account("VIN004",LocalDate.now().plusDays(3), 6000);

			alexandraAccount1.setClient(alexandraClient);
			alexandraAccount2.setClient(alexandraClient);

			accountRepository.save(alexandraAccount1);
			accountRepository.save(alexandraAccount2);

			// Create transactions for Melba's accounts
			Transaction melbaTransaction1 = new Transaction(melbaAccount1,CREDIT, 1500, "Bank deposit", LocalDateTime.now());
			Transaction melbaTransaction2 = new Transaction(melbaAccount1,DEBIT, -300, "buys", LocalDateTime.now());
			Transaction melbaTransaction3 = new Transaction(melbaAccount2,CREDIT, 2000,"Bank deposit", LocalDateTime.now());
			Transaction melbaTransaction4 = new Transaction(melbaAccount2,DEBIT, -700,"buys", LocalDateTime.now());

			transactionRepository.save(melbaTransaction1);
			transactionRepository.save(melbaTransaction2);
			transactionRepository.save(melbaTransaction3);
			transactionRepository.save(melbaTransaction4);

			// Create transactions for Alexandra's accounts
			Transaction alexandraTransaction1 = new Transaction(alexandraAccount1,CREDIT, 800,"Bank deposit", LocalDateTime.now());
			Transaction alexandraTransaction2 = new Transaction(alexandraAccount1,DEBIT, -200, "buys", LocalDateTime.now());
			Transaction alexandraTransaction3 = new Transaction(alexandraAccount2,CREDIT, 1200,"Bank deposit", LocalDateTime.now());
			Transaction alexandraTransaction4 = new Transaction(alexandraAccount2,DEBIT, -500,"buys", LocalDateTime.now());

			transactionRepository.save(alexandraTransaction1);
			transactionRepository.save(alexandraTransaction2);
			transactionRepository.save(alexandraTransaction3);
			transactionRepository.save(alexandraTransaction4);
		};

	}

}