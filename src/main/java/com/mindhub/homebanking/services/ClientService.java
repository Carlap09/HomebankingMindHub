package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    Client findByEmail(String email);

    void save(Client newClient);

    Optional<Client> findById(Long id);

    List<Client> findAll();

    Optional<Object> getClients();

    Optional<Client> getClientEmail(String name);

    Client getClientByEmail(String email);

    void saveClient(Client client);
}

