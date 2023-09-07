package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    CardService cardService;
    @Autowired
    ClientService clientService;


    @RequestMapping("/cards")
    public List<CardDTO> cardDTo() {
        return cardService.findAll().stream().map(CardDTO::new).collect(Collectors.toList());
    }

    @RequestMapping(path = "clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(Authentication authentication,
                                             @RequestParam CardType cardType,
                                             @RequestParam CardColor cardColor) {

        String email = authentication.getName();

        Client client = clientService.findByEmail(email);

        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }

        List<Card> cardsOfType = client.getCards().stream()
                .filter(card -> card.getType() == cardType)
                .collect(Collectors.toList());

        if (cardsOfType.size() >= 3) {
            return new ResponseEntity<>("You already have 3 cards of this type", HttpStatus.FORBIDDEN);
        }

        String cardNumber = generateCardNumber();
        Short cvv = generateRandomCVV();
        LocalDateTime thruDate = LocalDateTime.now().plusYears(5);
        LocalDateTime fromDate = LocalDateTime.now();

        Card newCard = new Card(client, client.getFirstName() + " " + client.getLastName(), cardType, cardColor, cardNumber, cvv, fromDate.toLocalDate(), thruDate.toLocalDate());
        cardService.save(newCard);

        return new ResponseEntity<>("You have created a card successfully", HttpStatus.CREATED);
    }

    private String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            cardNumber.append(getRandomNumber(1000, 9999));
            if (i < 3) {
                cardNumber.append("-");
            }
        }
        return cardNumber.toString();
    }

    private Short generateRandomCVV() {
        return (short) getRandomNumber(100, 999);
    }

    private int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

}