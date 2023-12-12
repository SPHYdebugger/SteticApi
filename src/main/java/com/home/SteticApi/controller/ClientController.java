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



    // Obtener todos los clientes o filtrar uno por el nombre
    @GetMapping("/clients")
    public ResponseEntity<List<Client>> findAll(@RequestParam(defaultValue = "") String firstname) throws ClientNotFoundException {
        if (!firstname.isEmpty()) {
            List<Client> clients = clientService.findClientByFirstname(firstname);
            return new ResponseEntity<>(clients, HttpStatus.OK);
        }

        List<Client> allClients = clientService.findAll();
        return new ResponseEntity<>(allClients, HttpStatus.OK);
    }

    // Obtener un cliente por ID
    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> findById(@PathVariable long clientId) throws ClientNotFoundException {
        Optional<Client> optionalClient = clientService.findById(clientId);
        Client client = optionalClient.orElseThrow(() -> new ClientNotFoundException(clientId));
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    // Obtener un cliente por el DNI
    @GetMapping("/clients/{DNI}")
    public ResponseEntity<Client> findByDNI(@PathVariable String DNI) throws ClientNotFoundException {
        Optional<Client> optionalClient = clientService.findByDNI(DNI);
        Client client = optionalClient.orElseThrow(() -> new ClientNotFoundException(DNI));
        return new ResponseEntity<>(client, HttpStatus.OK);
    }



    // Añadir un nuevo cliente
    @PostMapping("/clients")
    public ResponseEntity<Client> saveClient(@RequestBody Client client) {
        clientService.saveClient(client);
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }


    // Borrar un cliente por DNI
    @DeleteMapping("/clients/{DNI}")
    public ResponseEntity<Void> removeClient(@PathVariable String DNI) throws ClientNotFoundException {
        Optional<Client> optionalClient = clientService.findByDNI(DNI);
        if (optionalClient.isPresent()) {
            clientService.removeClient(DNI);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new ClientNotFoundException(DNI);
        }
    }


    // Modificar un cliente por DNI
    @PutMapping("/client/{DNI}")
    public ResponseEntity<Client> modifyClient(@RequestBody Client client, @PathVariable String DNI) throws ClientNotFoundException {
        Optional<Client> optionalClient = clientService.findByDNI(DNI);
        if (optionalClient.isPresent()) {
            clientService.modifyClient(client, DNI);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new ClientNotFoundException(DNI);
        }
    }



    // Control de excepciones
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponse> clientNotFoundException(ClientNotFoundException pnfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, pnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
