package com.logitrack.inventoryservice.service;

import com.logitrack.inventoryservice.entities.Product;
import com.logitrack.inventoryservice.exceptions.NoSuchProductException;
import com.logitrack.inventoryservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findProductById(int productId) {
        return productRepository.findProductById(productId)
                .orElseThrow(() -> new NoSuchProductException("Product with id: " + productId + " not found"));
    }
}
