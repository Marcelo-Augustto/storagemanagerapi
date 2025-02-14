package com.example.storagemanagerapi.service;

import com.example.storagemanagerapi.exception.stock.InsufficientStockException;
import com.example.storagemanagerapi.exception.stock.StockNotFoundException;
import com.example.storagemanagerapi.model.Product;
import com.example.storagemanagerapi.model.Stock;
import com.example.storagemanagerapi.repository.ProductRepository;
import com.example.storagemanagerapi.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;

    public Stock updateStock(Long productId, int quantityChange) {
        Stock stock = stockRepository.findByProductId(productId)
                .orElseThrow(() -> new StockNotFoundException(productId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found for stock update")); // Should never happen if DB is consistent

        if (stock.getQuantity() + quantityChange < 0) {
            throw new InsufficientStockException(product.getName());
        }

        stock.setQuantity(stock.getQuantity() + quantityChange);
        return stockRepository.save(stock);
    }

    public boolean isStockLow(Long productId) {
        Stock stock = stockRepository.findByProductId(productId)
                .orElseThrow(() -> new StockNotFoundException(productId));
        return stock.getQuantity() < stock.getMinimumStockLevel();
    }

    public Stock getStockByProductId(Long productId) {
        return stockRepository.findByProductId(productId)
                .orElseThrow(() -> new StockNotFoundException(productId));
    }
}
