package com.home.SteticApi.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOutDto {

    private long id;
    private String name;
    private String description;
    private float price;
    private LocalDate registrationDate;
}
