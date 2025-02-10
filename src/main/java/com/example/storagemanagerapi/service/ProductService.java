package com.example.storagemanagerapi.service;

import com.example.storagemanagerapi.model.Product;
import com.example.storagemanagerapi.model.Stock;
import com.example.storagemanagerapi.repository.ProductRepository;
import com.example.storagemanagerapi.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;


    public ProductService(ProductRepository productRepository, StockRepository stockRepository) {
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product createProduct(Product product) {
        Product savedProduct = productRepository.save(product);

        Stock stock = new Stock();
        stock.setProduct(savedProduct);
        stock.setQuantity(0);
        stock.setMinimumStockLevel(1); // Default alert level

        stockRepository.save(stock);
        return savedProduct;
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setDescription(updatedProduct.getDescription());
                    product.setCategory(updatedProduct.getCategory());
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
