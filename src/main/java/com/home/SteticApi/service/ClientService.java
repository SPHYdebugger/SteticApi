package com.home.SteticApi.service;

import com.home.SteticApi.domain.Client;
import com.home.SteticApi.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getClients() {
        return clientRepository.findAll();
    }


    public void saveClient(Client client) {
        clientRepository.save(client);
    }
}
