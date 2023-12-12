package com.home.SteticApi.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String firstname;
    @Column
    private String lastname;
    @Column
    private String DNI;
    @Column
    private String city;
    @Column
    private String street;
    @Column
    private int numHouse;
    @Column
    private int age;
    @Column
    private float height;
    @Column
    private LocalDate birthday;
    @Column
    private boolean VIP;


    @OneToMany(mappedBy = "client")
    private List<Order> orders;
}
