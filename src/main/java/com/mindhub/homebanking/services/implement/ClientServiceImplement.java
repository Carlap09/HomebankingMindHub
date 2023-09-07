package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImplement  implements ClientService {

    @Autowired
    ClientRepository clientRepository;


    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public void save(Client newClient) {

    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public List<Client> findAll(){
        return clientRepository.findAll() ;
    }

    @Override
    public Optional<Object> getClients() {
        return Optional.empty();
    }

    @Override
    public Optional<Client> getClientEmail(String name) {
        return Optional.empty();
    }


}
