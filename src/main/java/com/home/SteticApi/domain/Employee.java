package com.home.SteticApi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private String DNI;
    @Column
    private int age;
    @Column
    private float height;
    @Column
    private boolean academicTitle;
    @Column(name = "register_date")
    private LocalDate registerDate;

    @JsonBackReference("employee_shop")
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;


}
