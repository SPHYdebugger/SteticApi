package com.home.SteticApi.repository;

import com.home.SteticApi.domain.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
    List<Client> findAll();
    List<Client> findByFirstname(String firstname);

    Optional<Client> findById(long id);
    Optional<Client> findByDNI(String DNI);
    List<Client> findByFirstnameAndDNI(String firstname, String DNI);

    Optional<Client> deleteByDNI(String DNI);
}
