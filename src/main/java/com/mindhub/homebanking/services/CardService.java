package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Card;


import java.util.List;
import java.util.Optional;

public interface CardService {


    void save(Card newCard);

    List <Card>findAll();

    Optional<Object> findById(Long id);
}
