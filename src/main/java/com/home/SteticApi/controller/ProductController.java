package com.home.SteticApi.controller;

import com.home.SteticApi.domain.ErrorResponse;
import com.home.SteticApi.domain.Product;
import com.home.SteticApi.exception.ProductException.ProductNotFoundException;
import com.home.SteticApi.service.ProductService;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productId}")
    public Product findById(@PathVariable long productId) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productService.findProductById(productId);
        Product product = optionalProduct.orElseThrow(() -> new ProductNotFoundException(productId));
        return product;
    }

    @GetMapping("/products")
    public List<Product> findAll(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") float price
    ) {
        if (!(name.isEmpty()) && (price == 0)) {
            return productService.findProductsByName(name);
        } else if (!name.isEmpty() && (price != 0)) {
            return productService.findProductByNameAndPrice(name, price);
        }

        return productService.findAll();
    }


    @PostMapping("/products")
    public void saveProduct(@RequestBody Product product) {
        productService.saveProduct(product);
    }

    @DeleteMapping("/product/{productId}")
    public void removeProduct(@PathVariable long productId) {
        productService.removeProduct(productId);
    }

    @PutMapping("/product/{productId}")
    public void modifyProduct(@RequestBody Product product, @PathVariable long productId) {
        productService.modifyProduct(product, productId);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> productNotFoundException(ProductNotFoundException pnfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, pnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
