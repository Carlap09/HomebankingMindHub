package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;


@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);}

	//@Bean
	//public CommandLineRunner initData(ClientRepository clientRepository) {
	//	return (args) -> {
	//		// save a couple of customers
	//		clientRepository.save(new Client("Melba", "Morel", "melba@mindhub.com"));
	//		clientRepository.save(new Client("Alexandra", "Pacheco", "AlexandraA@gmail.com"));

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository) {
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


		};

	}




}