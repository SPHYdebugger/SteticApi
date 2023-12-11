package com.home.SteticApi.repository;

import com.home.SteticApi.domain.Client;
import com.home.SteticApi.domain.Order;
import java.util.List;
import java.util.Optional;

import com.home.SteticApi.domain.Product;
import com.home.SteticApi.dto.OrderOutDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findAll();
    Optional<Order> findById(long id);
    List<Order> findByClient(Client client);
    Order findByNumber(String number);

    List<Order> findByProducts(Product product);
}
