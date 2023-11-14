package com.home.SteticApi.controller;

import com.home.SteticApi.domain.Product;
import com.home.SteticApi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<Product> getAll() {
        return productService.getProducts();
    }

    @PostMapping("/products")
    public void saveProduct(@RequestBody Product product) {
        productService.saveProduct(product);
    }
}
