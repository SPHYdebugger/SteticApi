package com.home.SteticApi.service;

import com.home.SteticApi.domain.Product;
import com.home.SteticApi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }
    public List<Product> getProductsByPrice(float price){
        return productRepository.findByPrice(price);
    }

    public List<Product> getProductByNameAndPrice(String name, float price) {
        return productRepository.findByNameAndPrice(name, price);
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public void removeProduct(long productId) {
        productRepository.deleteById(productId);
    }

  public void modifyProduct(Product newProduct, long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
          Product existingProduct = product.get();
          existingProduct.setName(newProduct.getName());
          existingProduct.setDescription(newProduct.getDescription());
          existingProduct.setPrice(newProduct.getPrice());
          productRepository.save(existingProduct);
        }
    }
}