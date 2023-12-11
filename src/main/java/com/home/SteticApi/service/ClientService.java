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

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> findById(long id) {
        return clientRepository.findById(id);
    }
    public Optional<Client> findByDNI(String DNI) {
        return clientRepository.findByDNI(DNI);
    }

    public List<Client> findClientByFirstname(String firstname) {
        return clientRepository.findByFirstname(firstname);
    }

    public List<Client> findClientByFirstnameAndDNI(String firstname, String DNI) {
        return clientRepository.findByFirstnameAndDNI(firstname, DNI);
    }

    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    public void removeClient(String DNI) {
        clientRepository.deleteByDNI(DNI);
    }

    public void modifyClient(Client newClient, String DNI) {
        Optional<Client> client = clientRepository.findByDNI(DNI);
        if (client.isPresent()) {
            Client existingClient = client.get();
            existingClient.setFirstname(newClient.getFirstname());
            existingClient.setLastname(newClient.getLastname());
            existingClient.setCity(newClient.getCity());
            existingClient.setStreet(newClient.getStreet());
            existingClient.setNumHouse(newClient.getNumHouse());
            existingClient.setAge(newClient.getAge());
            clientRepository.save(existingClient);
        }
    }
}
