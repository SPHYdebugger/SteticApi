package com.home.SteticApi.controller;

import com.home.SteticApi.domain.Client;
import com.home.SteticApi.domain.ErrorResponse;

import com.home.SteticApi.exception.ClientException.ClientNotFoundException;

import com.home.SteticApi.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/clients/{clientId}")
    public Client findById(@PathVariable long clientId) throws ClientNotFoundException {
        Optional<Client> optionalClient = clientService.findById(clientId);
        Client client = optionalClient.orElseThrow(() -> new ClientNotFoundException(clientId));
        return client;
    }

    @GetMapping("/clients")
    public List<Client> findAll(@RequestParam(defaultValue = "") String firstname) {
        if (!firstname.isEmpty()) {
            return clientService.findClientByFirstname(firstname);
        }

        return clientService.findAll();
    }

    @PostMapping("/clients")
    public void saveClient(@RequestBody Client client) {
        clientService.saveClient(client);
    }

    @DeleteMapping("/clients/{clientId}")
    public void removeClient(@PathVariable long clientId) {
        clientService.removeClient(clientId);
    }

    @PutMapping("/client/{clientId}")
    public void modifyClient(@RequestBody Client client, @PathVariable long clientId) {
        clientService.modifyClient(client, clientId);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponse> clientNotFoundException(ClientNotFoundException pnfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, pnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
