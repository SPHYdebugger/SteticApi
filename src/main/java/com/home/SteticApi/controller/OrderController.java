package com.home.SteticApi.controller;

import com.home.SteticApi.domain.Client;
import com.home.SteticApi.domain.ErrorResponse;
import com.home.SteticApi.domain.Order;
import com.home.SteticApi.domain.Product;
import com.home.SteticApi.dto.OrderInDto;
import com.home.SteticApi.exception.ClientException.ClientNotFoundException;
import com.home.SteticApi.exception.OrderException.OrderNotFoundException;
import com.home.SteticApi.exception.ProductException.ProductNotFoundException;
import com.home.SteticApi.service.ClientService;
import com.home.SteticApi.service.OrderService;
import com.home.SteticApi.service.ProductService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class OrderController {

    @Autowired
    private ClientService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    // Obtener todas las compras
    @GetMapping("/orders")
    public List<Order> findOrders() {
        return orderService.findAll();
    }

    // Obtener los datos de una compra
    @GetMapping("/orders/{orderId}")
    public Order findById(@PathVariable long orderId)
            throws ClientNotFoundException, OrderNotFoundException {
        Optional<Order> optionalOrder = orderService.findById(orderId);
        Order order = optionalOrder.orElseThrow(() -> new OrderNotFoundException(orderId));
        return order;
    }

    // Obtener todas las compras de un cliente
    @GetMapping("/client/{clientId}/orders")
    public List<Order> findClientOrders(@PathVariable long clientId) throws ClientNotFoundException {
        return orderService.findByClient(clientId);
    }

    // Añadir una compra a un cliente
    @PostMapping("/client/{clientId}/orders")
    public void addOrder(@RequestBody OrderInDto orderInDto, @PathVariable long clientId) throws ClientNotFoundException, ProductNotFoundException {
        orderService.addOrder(orderInDto, clientId);
    }

    // TODO Hacer el resto de operaciones hasta el CRUD

    // Modificar una compra
    @PutMapping("/order/{ordertId}")
    public void modifyOrder(@RequestBody Order order, @PathVariable long orderId) {
        orderService.modifyOrder(order, orderId);
    }

    // Eliminar una compra por ID
    @DeleteMapping("/order/{orderId}")
    public void removeOrder(@PathVariable long orderId) {
        orderService.removeOrder(orderId);
    }

    // Control de excepciones
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponse> clientNotFoundException(ClientNotFoundException unfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, unfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> productNotFoundException(ProductNotFoundException pnfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, pnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
