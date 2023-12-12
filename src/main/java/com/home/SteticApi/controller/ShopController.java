package com.home.SteticApi.controller;

import com.home.SteticApi.domain.Shop;
import com.home.SteticApi.domain.ErrorResponse;
import com.home.SteticApi.exception.ShopException.ShopNotFoundException;
import com.home.SteticApi.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ShopController {

    @Autowired
    private ShopService shopService;

    // Obtener todas las tiendas
    @GetMapping("/shops")
    public List<Shop> findAllShops() {
        return shopService.findAll();
    }

    // Obtener los datos de una tienda por ID
    @GetMapping("/shops/{shopId}")
    public ResponseEntity<Shop> findShopById(@PathVariable long shopId) throws ShopNotFoundException {
        Optional<Shop> optionalShop = shopService.findById(shopId);

        if (optionalShop.isPresent()) {
            Shop shop = optionalShop.get();
            return new ResponseEntity<>(shop, HttpStatus.OK);
        } else {
            throw new ShopNotFoundException(shopId);
        }
    }

    // Añadir una nueva tienda
    @PostMapping("/shops")
    public ResponseEntity<Shop> addShop(@RequestBody Shop shop) {
        shopService.saveShop(shop);
        return new ResponseEntity<>(shop, HttpStatus.CREATED);
    }

    // Modificar una tienda por ID
    @PutMapping("/shops/{shopId}")
    public ResponseEntity<Void> modifyShop(@RequestBody Shop shop, @PathVariable long shopId) throws ShopNotFoundException {
        Optional<Shop> optionalShop = shopService.findById(shopId);

        if (optionalShop.isPresent()) {
            shopService.modifyShop(shop, shopId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new ShopNotFoundException(shopId);
        }
    }

    // Eliminar una tienda por ID
    @DeleteMapping("/shops/{shopId}")
    public ResponseEntity<Void> removeShop(@PathVariable long shopId) throws ShopNotFoundException {
        Optional<Shop> optionalShop = shopService.findById(shopId);

        if (optionalShop.isPresent()) {
            shopService.removeShop(shopId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new ShopNotFoundException(shopId);
        }
    }

    // Control de excepciones
    @ExceptionHandler(ShopNotFoundException.class)
    public ResponseEntity<ErrorResponse> shopNotFoundException(ShopNotFoundException snfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, snfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
