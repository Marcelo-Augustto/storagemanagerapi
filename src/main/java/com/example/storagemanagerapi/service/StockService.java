package com.example.storagemanagerapi.service;

import com.example.storagemanagerapi.model.Stock;
import com.example.storagemanagerapi.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock updateStock(Long productId, int quantityChange) {
        Stock stock = stockRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Stock not found"));
        stock.setQuantity(stock.getQuantity() + quantityChange);
        return stockRepository.save(stock);
    }

    public boolean isStockLow(Long productId) {
        Stock stock = stockRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Stock not found"));
        return stock.getQuantity() < stock.getMinimumStockLevel();
    }
}

