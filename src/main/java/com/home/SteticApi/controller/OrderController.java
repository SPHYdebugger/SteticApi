package com.home.SteticApi.controller;

import com.home.SteticApi.domain.Client;
import com.home.SteticApi.domain.ErrorResponse;
import com.home.SteticApi.domain.Order;
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

    @GetMapping("/orders")
    public List<Order> findOrders() {
        return orderService.findAll();
    }

    @GetMapping("/client/{clientId}/orders")
    public List<Order> findClientOrders(@PathVariable long clientId) throws ClientNotFoundException {
        return orderService.findByClient(clientId);
    }

  @GetMapping("/orders/{orderId}")
  public Order findById(@PathVariable long orderId)
      throws ClientNotFoundException, OrderNotFoundException {
        Optional<Order> optionalOrder = orderService.findById(orderId);
        Order order = optionalOrder.orElseThrow(() -> new OrderNotFoundException(orderId));
        return order;
    }

    @PostMapping("/client/{clientId}/orders")
    public void addOrder(@RequestBody OrderInDto orderInDto, @PathVariable long clientId) throws ClientNotFoundException, ProductNotFoundException {
        orderService.addOrder(orderInDto, clientId);
    }

    // TODO Hacer el resto de operaciones hasta el CRUD

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
