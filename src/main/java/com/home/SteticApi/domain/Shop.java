package com.home.SteticApi.domain;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "shops")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private String address;
    @Column
    private int employeesNumber;
    @Column
    private String city;
    @Column
    private float meters;
    @Column
    private boolean solarium;
    @Column
    private LocalDate openDate;


    @OneToMany(mappedBy = "shop")
    private List<Employee> employees;
}
