package com.example.storagemanagerapi.exception.stock;

public class StockNotFoundException extends RuntimeException {
    public StockNotFoundException(Long productId) {
        super("Stock record for product ID " + productId + " not found");
    }
}
