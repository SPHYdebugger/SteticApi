package com.home.SteticApi.controller;

import com.home.SteticApi.domain.ErrorResponse;
import com.home.SteticApi.domain.Product;
import com.home.SteticApi.exception.OrderException.OrderNotFoundException;
import com.home.SteticApi.exception.ProductException.ProductNotFoundException;
import com.home.SteticApi.service.ProductService;

import java.util.ArrayList;
import java.util.Collections;
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


    // Obtener todos los productos o filtrar uno por nombre o ID
    @GetMapping("/products")
    public ResponseEntity<List<Product>> findAll(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") long productId
    ) throws ProductNotFoundException {
        if (!name.isEmpty() && productId == 0) {
            List<Product> products = productService.findProductsByName(name);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else if (name.isEmpty() && productId != 0) {
            Optional<Product> optionalProduct = productService.findProductById(productId);
            Product product = optionalProduct.orElseThrow(() -> new ProductNotFoundException(productId));
            return new ResponseEntity<>(Collections.singletonList(product), HttpStatus.OK);
        }

        List<Product> allProducts = productService.findAll();
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }



    // Obtener un producto por la ID
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> findById(@PathVariable long productId) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productService.findProductById(productId);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            throw new ProductNotFoundException(productId);
        }
    }



    // Añadir un nuevo producto
    @PostMapping(value = "/products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        productService.saveProduct(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    // Eliminar un producto
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> removeProduct(@PathVariable long productId) {
        productService.removeProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Modificar un producto
    @PutMapping("/product/{productId}")
    public ResponseEntity<Void> modifyProduct(@RequestBody Product product, @PathVariable long productId) {
        productService.modifyProduct(product, productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }




    // Controlar las excepciones
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> productNotFoundException(ProductNotFoundException pnfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, pnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
