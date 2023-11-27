package com.home.SteticApi.service;

import com.home.SteticApi.domain.Client;

import com.home.SteticApi.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(long id) {
        return clientRepository.findById(id);
    }

    public List<Client> getClientByFirstname(String firstname) {
        return clientRepository.findByFirstname(firstname);
    }

    public List<Client> getClientByFirstnameAndDNI(String firstname, String DNI) {
        return clientRepository.findByFirstnameAndDNI(firstname, DNI);
    }

    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    public void removeClient(long clientId) {
        clientRepository.deleteById(clientId);
    }

    public void modifyClient(Client newClient, long clientId) {
        Optional<Client> client = clientRepository.findById(clientId);
        if (client.isPresent()) {
            Client existingClient = client.get();
            existingClient.setFirstname(newClient.getFirstname());
            existingClient.setLastname(newClient.getLastname());
            existingClient.setDNI(newClient.getDNI());
            existingClient.setCity(newClient.getCity());
            existingClient.setStreet(newClient.getStreet());
            existingClient.setNumHouse(newClient.getNumHouse());
            existingClient.setAge(newClient.getAge());
            clientRepository.save(existingClient);
        }
    }
}
