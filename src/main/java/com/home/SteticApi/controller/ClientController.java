package com.home.SteticApi.controller;

import com.home.SteticApi.domain.Client;
import com.home.SteticApi.domain.ErrorResponse;

import com.home.SteticApi.domain.Product;
import com.home.SteticApi.exception.ClientException.ClientNotFoundException;

import com.home.SteticApi.exception.ProductException.ProductNotFoundException;
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

    // Obtener todos los clientes o filtrar uno por el nombre
    @GetMapping("/clients")
    public List<Client> findAll(@RequestParam(defaultValue = "") String firstname) {
        if (!firstname.isEmpty()) {
            return clientService.findClientByFirstname(firstname);
        }
        return clientService.findAll();
    }


    // Obtener un cliente por el DNI
    @GetMapping("/clients/{DNI}")
    public Client findByDNI(@PathVariable String DNI) throws ClientNotFoundException {
        Optional<Client> optionalClient = clientService.findByDNI(DNI);
        Client client = optionalClient.orElseThrow(() -> new ClientNotFoundException(DNI));
        return client;
    }


    // Añadir un nuevo cliente
    @PostMapping("/clients")
    public void saveClient(@RequestBody Client client) {
        clientService.saveClient(client);
    }

    // Borrar un cliente por DNI
    @DeleteMapping("/clients/{DNI}")
    public void removeClient(@PathVariable String DNI) {
        clientService.removeClient(DNI);
    }

    // Modificar un cliente por DNI
    @PutMapping("/client/{DNI}")
    public void modifyClient(@RequestBody Client client, @PathVariable String DNI) {
        clientService.modifyClient(client, DNI);
    }


    // Control de excepciones
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponse> clientNotFoundException(ClientNotFoundException pnfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, pnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
