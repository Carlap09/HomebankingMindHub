package com.mindhub.homebanking.services.implement;


import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImplement implements CardService {

    @Autowired
    CardRepository cardRepository;


    @Override
    public void save(Card newCard) {
        cardRepository.save(newCard);

    }

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @Override
    public Optional<Object> findById(Long id) {
        return Optional.empty();
    }
}
