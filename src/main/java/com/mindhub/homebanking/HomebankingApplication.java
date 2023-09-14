package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {


	public static void main(String[] args) {

		SpringApplication.run(HomebankingApplication.class, args);}


	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository ) {
		return (args) -> {

			Client melbaClient = new Client("Melba", "Morel", "melba@mindhub.com",passwordEncoder.encode("1234"));
    		Client alexandraClient = new Client("Alexandra", "Araujo", "alexandraa@gmail.com",passwordEncoder.encode("4321"));
			Client adminClient = new Client("admin", "admin", "admin@admin.com", passwordEncoder.encode("7894"));


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
			Transactions melbaTransactions1 = new Transactions(melbaAccount1, TransactionsType.CREDIT, 1500, "Bank deposit", LocalDateTime.now());
			Transactions melbaTransactions2 = new Transactions(melbaAccount1, TransactionsType.DEBIT, -300, "buys", LocalDateTime.now());
			Transactions melbaTransactions3 = new Transactions(melbaAccount2, TransactionsType.CREDIT, 2000, "Bank deposit", LocalDateTime.now());
			Transactions melbaTransactions4 = new Transactions(melbaAccount2, TransactionsType.DEBIT, -700, "buys", LocalDateTime.now());

			transactionRepository.save(melbaTransactions1);
			transactionRepository.save(melbaTransactions2);
			transactionRepository.save(melbaTransactions3);
			transactionRepository.save(melbaTransactions4);

			// Create transactions for Alexandra's accounts
			Transactions alexandraTransactions1 = new Transactions(alexandraAccount1, TransactionsType.CREDIT, 800, "Bank deposit", LocalDateTime.now());
			Transactions alexandraTransactions2 = new Transactions(alexandraAccount1, TransactionsType.DEBIT, -200, "buys", LocalDateTime.now());
			Transactions alexandraTransactions3 = new Transactions(alexandraAccount2, TransactionsType.CREDIT, 1200, "Bank deposit", LocalDateTime.now());
			Transactions alexandraTransactions4 = new Transactions(alexandraAccount2, TransactionsType.DEBIT, -500, "buys", LocalDateTime.now());

			transactionRepository.save(alexandraTransactions1);
			transactionRepository.save(alexandraTransactions2);
			transactionRepository.save(alexandraTransactions3);
			transactionRepository.save(alexandraTransactions4);

			// Create loans and save them in the database

			List<Integer> mortgagePayments = Arrays.asList(12, 24, 36, 48, 60);
			List<Integer> personalPayments = Arrays.asList(6, 12, 24);
			List<Integer> carPayments = Arrays.asList(6, 12, 24, 36);

			Loan mortgageLoan = new Loan(null,"Mortgage", 500000, mortgagePayments);
			Loan personalLoan = new Loan(null,"Personal", 100000, personalPayments);
			Loan carLoan = new Loan(null,"Car", 300000, carPayments);

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





