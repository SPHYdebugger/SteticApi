package com.home.SteticApi.controller;

import com.home.SteticApi.domain.Client;
import com.home.SteticApi.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public List<Client> getAll() {
        return clientService.getClients();
    }

    @PostMapping("/products")
    public void saveClient(@RequestBody Client client) {
        clientService.saveClient(client);
    }
}
