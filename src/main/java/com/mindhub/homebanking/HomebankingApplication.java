package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {

		SpringApplication.run(HomebankingApplication.class, args);}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository ) {
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
			Account alexandraAccount1 = new Account("VIN003", LocalDate.now().plusDays(2), 3000);
			Account alexandraAccount2 = new Account("VIN004", LocalDate.now().plusDays(3), 6000);

			alexandraAccount1.setClient(alexandraClient);
			alexandraAccount2.setClient(alexandraClient);

			accountRepository.save(alexandraAccount1);
			accountRepository.save(alexandraAccount2);

			// Create transactions for Melba's accounts
			Transaction melbaTransaction1 = new Transaction(melbaAccount1,TransactionType.CREDIT, 1500, "Bank deposit", LocalDateTime.now());
			Transaction melbaTransaction2 = new Transaction(melbaAccount1, TransactionType.DEBIT, -300, "buys", LocalDateTime.now());
			Transaction melbaTransaction3 = new Transaction(melbaAccount2, TransactionType.CREDIT, 2000, "Bank deposit", LocalDateTime.now());
			Transaction melbaTransaction4 = new Transaction(melbaAccount2, TransactionType.DEBIT, -700, "buys", LocalDateTime.now());

			transactionRepository.save(melbaTransaction1);
			transactionRepository.save(melbaTransaction2);
			transactionRepository.save(melbaTransaction3);
			transactionRepository.save(melbaTransaction4);

			// Create transactions for Alexandra's accounts
			Transaction alexandraTransaction1 = new Transaction(alexandraAccount1, TransactionType.CREDIT, 800, "Bank deposit", LocalDateTime.now());
			Transaction alexandraTransaction2 = new Transaction(alexandraAccount1, TransactionType.DEBIT, -200, "buys", LocalDateTime.now());
			Transaction alexandraTransaction3 = new Transaction(alexandraAccount2, TransactionType.CREDIT, 1200, "Bank deposit", LocalDateTime.now());
			Transaction alexandraTransaction4 = new Transaction(alexandraAccount2, TransactionType.DEBIT, -500, "buys", LocalDateTime.now());

			transactionRepository.save(alexandraTransaction1);
			transactionRepository.save(alexandraTransaction2);
			transactionRepository.save(alexandraTransaction3);
			transactionRepository.save(alexandraTransaction4);

			// Create loans and save them in the database

			List<Integer> mortgagePayments = Arrays.asList(12, 24, 36, 48, 60);
			List<Integer> personalPayments = Arrays.asList(6, 12, 24);
			List<Integer> carPayments = Arrays.asList(6, 12, 24, 36);

			Loan mortgageLoan = new Loan("Mortgage", 500000.00, mortgagePayments);
			Loan personalLoan = new Loan("Personal", 100000.00, personalPayments);
			Loan carLoan = new Loan("Car", 300000.00, carPayments);

			loanRepository.save(mortgageLoan);
			loanRepository.save(personalLoan);
			loanRepository.save(carLoan);

			// Create ClientLoan  for Melba
			ClientLoan melbaMortgage = new ClientLoan(400000.00, 60, melbaClient, mortgageLoan);
			ClientLoan melbaPersonal = new ClientLoan(50000.00, 12, melbaClient, personalLoan);



			clientLoanRepository.save(melbaMortgage);
			clientLoanRepository.save(melbaPersonal);

			melbaClient.getClientLoans().add(melbaMortgage);
			melbaClient.getClientLoans().add(melbaPersonal);
			clientRepository.save(melbaClient);

			// Create ClientLoan  for Alexandra
			ClientLoan alexandraPersonal = new ClientLoan(100000.00, 24, alexandraClient, personalLoan);
			ClientLoan alexandraCar = new ClientLoan(200000.00, 36, alexandraClient, carLoan);


			clientLoanRepository.save(alexandraPersonal);
			clientLoanRepository.save(alexandraCar);

			alexandraClient.getClientLoans().add(alexandraPersonal);
			alexandraClient.getClientLoans().add(alexandraCar);
			clientRepository.save(alexandraClient);

			// Create cards for the client Melba
			LocalDate currentDate = LocalDate.now();
			Card melbaDebitCard1 = new Card(melbaClient,melbaClient.getFirstName() + " " + melbaClient.getLastName(),CardType.DEBIT, CardColor.GOLD, "1234567812345678", (short) 235, currentDate, currentDate.plusYears(5));
			Card melbaCreditCard2 = new Card(melbaClient,melbaClient.getFirstName() + " " + melbaClient.getLastName(),CardType.CREDIT, CardColor.TITANIUM, "9876543298765432", (short) 456, currentDate, currentDate.plusYears(5));

			cardRepository.save(melbaDebitCard1);
			cardRepository.save(melbaCreditCard2);

			melbaClient.getCards().add(melbaDebitCard1);
			melbaClient.getCards().add(melbaCreditCard2);
			clientRepository.save(melbaClient);

			// Create cards for the client Alexandra
			Card alexandraCreditCard1 = new Card(alexandraClient,alexandraClient.getFirstName() + " " + alexandraClient.getLastName(),CardType.CREDIT, CardColor.SILVER, "5678901256789012", (short) 378, currentDate, currentDate.plusYears(5));

			cardRepository.save(alexandraCreditCard1);

			alexandraClient.getCards().add(alexandraCreditCard1);
			clientRepository.save(alexandraClient);
		};

	}

}





