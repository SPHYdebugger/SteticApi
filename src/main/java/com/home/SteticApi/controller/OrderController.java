package com.home.SteticApi.controller;

import com.home.SteticApi.domain.Client;
import com.home.SteticApi.domain.ErrorResponse;
import com.home.SteticApi.domain.Order;
import com.home.SteticApi.domain.Product;
import com.home.SteticApi.dto.OrderInDto;
import com.home.SteticApi.dto.OrderOutDto;
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
    private ClientService clientService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    // Obtener todas las compras
    @GetMapping("/orders")
    public List<Order> findOrdersDto() {
        return orderService.findAll();
    }

    // Obtener los datos de una compra
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Order> findById(@PathVariable long orderId) throws OrderNotFoundException {
        Optional<Order> optionalOrder = orderService.findById(orderId);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            throw new OrderNotFoundException(orderId);
        }
    }



    // Obtener todas las compras de un cliente
    @GetMapping("/client/{clientId}/orders")
    public ResponseEntity<List<Order>> findClientOrders(@PathVariable long clientId) throws ClientNotFoundException {
        Optional<Client> optionalClient = clientService.findById(clientId);

        if (optionalClient.isPresent()) {
            List<Order> orders = orderService.findByClient(clientId);

            if (!orders.isEmpty()) {
                return new ResponseEntity<>(orders, HttpStatus.OK);
            } else {
                // El cliente existe pero no tiene compras
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } else {
            // El cliente no existe
            throw new ClientNotFoundException(clientId);
        }
    }


    // TODO compras de un producto
    // Obtener todas las compras de un producto
    //@GetMapping("/product/{productId}/orders")
    //public List<OrderOutDto> findByProduct(@PathVariable long productId) throws ProductNotFoundException {
    //    List<Order> ordersWithProduct = orderService.findByProducts(productId);

      //  return listDtos;
    //}

    // Añadir una compra a un cliente
    @PostMapping("/client/{clientId}/orders")
    public ResponseEntity<Void> addOrder(@RequestBody OrderInDto orderInDto, @PathVariable long clientId)
            throws ClientNotFoundException, ProductNotFoundException {
        Optional<Client> optionalClient = clientService.findById(clientId);

        if (optionalClient.isPresent()) {
            orderService.addOrder(orderInDto, clientId);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            // El cliente no existe
            throw new ClientNotFoundException(clientId);
        }
    }


    // Modificar una compra
    @PutMapping("/order/{orderId}")
    public ResponseEntity<Void> modifyOrder(@RequestBody Order order, @PathVariable long orderId)
            throws OrderNotFoundException {
        Optional<Order> optionalOrder = orderService.findById(orderId);

        if (optionalOrder.isPresent()) {
            orderService.modifyOrder(order, orderId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            // La orden no existe
            throw new OrderNotFoundException(orderId);
        }
    }


    // Eliminar una compra por ID
    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<Void> removeOrder(@PathVariable long orderId)
            throws OrderNotFoundException {
        Optional<Order> optionalOrder = orderService.findById(orderId);

        if (optionalOrder.isPresent()) {
            orderService.removeOrder(orderId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            // La orden no existe
            throw new OrderNotFoundException(orderId);
        }
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
