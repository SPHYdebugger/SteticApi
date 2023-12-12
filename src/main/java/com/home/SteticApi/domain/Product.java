package com.home.SteticApi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private int size;
    @Column
    private String description;
    @Column
    private float price;
    @Column(name = "registration_date")
    private LocalDate registrationDate;
    @Column
    private boolean dangerous;

    @ManyToMany(mappedBy = "products")
    private List<Order> orders;
}
