package com.example.storagemanagerapi.controller;

import com.example.storagemanagerapi.model.Stock;
import com.example.storagemanagerapi.service.StockService;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/stocks")
@Tag(name = "Stock Management", description = "APIs for managing stock levels")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PutMapping("/update/{productId}")
    @Operation(summary = "Update stock", description = "Updates stock quantity for a product")
    public Stock updateStock(@PathVariable Long productId, @RequestParam int quantityChange) {
        return stockService.updateStock(productId, quantityChange);
    }

    @GetMapping("/low-stock/{productId}")
    @Operation(summary = "Check low stock", description = "Checks if stock for a product is low")
    public boolean checkStockLevel(@PathVariable Long productId) {
        return stockService.isStockLow(productId);
    }
}