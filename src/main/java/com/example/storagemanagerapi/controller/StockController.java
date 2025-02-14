package com.example.storagemanagerapi.controller;

import com.example.storagemanagerapi.model.Stock;
import com.example.storagemanagerapi.service.StockService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;

@RestController
@RequestMapping("/api/stocks")
@Tag(name = "Stock Management", description = "APIs for managing stock levels")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @PutMapping("/update/{productId}")
    @Operation(summary = "Update stock", description = "Updates stock quantity for a product")
    public ResponseEntity<Stock> updateStock(
            @PathVariable Long productId,
            @RequestParam int quantityChange) {
        Stock updatedStock = stockService.updateStock(productId, quantityChange);
        return ResponseEntity.ok(updatedStock);
    }

    @GetMapping("/low-stock/{productId}")
    @Operation(summary = "Check low stock", description = "Checks if stock for a product is low")
    public ResponseEntity<Map<String, Boolean>> checkStockLevel(@PathVariable Long productId) {
        boolean isLow = stockService.isStockLow(productId);
        return ResponseEntity.ok(Map.of("isLowStock", isLow));
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Get stock details", description = "Fetches stock details for a given product")
    public ResponseEntity<Stock> getStockByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(stockService.getStockByProductId(productId));
    }
}
