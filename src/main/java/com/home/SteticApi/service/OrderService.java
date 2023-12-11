package com.home.SteticApi.service;

import com.home.SteticApi.domain.Client;
import com.home.SteticApi.domain.Order;
import com.home.SteticApi.domain.Product;
import com.home.SteticApi.dto.OrderInDto;
import com.home.SteticApi.dto.OrderOutDto;
import com.home.SteticApi.exception.ClientException.ClientNotFoundException;
import com.home.SteticApi.exception.ProductException.ProductNotFoundException;
import com.home.SteticApi.repository.OrderRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ClientService clientService;


    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<Order> findByClient(long clientId) throws ClientNotFoundException {
        Client client = clientService.findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
        return orderRepository.findByClient(client);
    }

    public List<Order> findByProducts(long productId) throws ProductNotFoundException {

        List<Order> allOrders = orderRepository.findAll();
        List<Order> ordersWithProduct = new ArrayList<>();

        for (Order order : allOrders) {
            List<Product> productsOfOrder = order.getProducts();
            for (Product product : productsOfOrder) {
                if (product.getId() == productId) {

                    ordersWithProduct.add(order);
                    break;
                }
            }
        }

        if (ordersWithProduct.isEmpty()) {
            throw new ProductNotFoundException("No se encontraron compras con el producto con ID " + productId);
        }
        return ordersWithProduct;
    }




    public Optional<Order> findById(long id) {
        return orderRepository.findById(id);
    }

    public void addOrder(OrderInDto orderInDto, long clientId) throws ClientNotFoundException, ProductNotFoundException {
        Order order = new Order();

        Client client = clientService.findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
        order.setClient(client);

        List<Product> products = new ArrayList<>();
        for (long productId: orderInDto.getProductIds()) {
            Product product = productService.findProductById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
            products.add(product);
        }
        order.setProducts(products);

        order.setNumber(UUID.randomUUID().toString());
        order.setCreationDate(LocalDate.now());
        orderRepository.save(order);
    }

    public void modifyOrder(Order newOrder, long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            Order existingOrder = order.get();
            existingOrder.setClient(newOrder.getClient());
            existingOrder.setProducts(newOrder.getProducts());
            existingOrder.setCreationDate(newOrder.getCreationDate());
            orderRepository.save(existingOrder);
        }
    }

    public void removeOrder(long orderId) {
        orderRepository.deleteById(orderId);
    }

    // TODO hacer el resto de métodos hasta el CRUD
}
