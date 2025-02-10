package com.example.storagemanagerapi.controller;

import com.example.storagemanagerapi.model.Stock;
import com.example.storagemanagerapi.service.StockService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stocks")
class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PutMapping("/update/{productId}")
    public Stock updateStock(@PathVariable Long productId, @RequestParam int quantityChange) {
        return stockService.updateStock(productId, quantityChange);
    }

    @GetMapping("/low-stock/{productId}")
    public boolean checkStockLevel(@PathVariable Long productId) {
        return stockService.isStockLow(productId);
    }
}