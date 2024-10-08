package com.logitrack.inventoryservice.service;

import com.logitrack.inventoryservice.entities.Product;
import com.logitrack.inventoryservice.exceptions.NoSuchProductException;
import com.logitrack.inventoryservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с продуктами.
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Поиск продукта по его ID.
     *
     * @param productId ID продукта
     * @return продукт, если он найден, иначе выбрасывает исключение NoSuchProductException
     */
    public Product findProductById(int productId) {
        return productRepository.findProductById(productId)
                .orElseThrow(() -> new NoSuchProductException("Product with id: " + productId + " not found"));
    }
}
