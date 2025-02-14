package com.example.storagemanagerapi.controller;

import com.example.storagemanagerapi.model.Report;
import com.example.storagemanagerapi.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;


@RestController
@RequestMapping("/api/reports")
@Tag(name = "Reports", description = "APIs for generating reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    @Operation(summary = "Get all reports", description = "Retrieves a list of all reports")
    public ResponseEntity<List<Report>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @PostMapping("/stock-movement")
    @Operation(summary = "Generate stock movement report", description = "Creates a report for stock movement")
    public ResponseEntity<Report> generateStockMovementReport() {
        return ResponseEntity.ok(reportService.generateStockMovementReport());
    }

    @PostMapping("/best-selling")
    @Operation(summary = "Generate best-selling products report", description = "Creates a report for best-selling products")
    public ResponseEntity<Report> generateBestSellingProductsReport() {
        return ResponseEntity.ok(reportService.generateBestSellingProductsReport());
    }

    @PostMapping("/revenue")
    @Operation(summary = "Generate revenue report", description = "Creates a revenue report")
    public ResponseEntity<Report> generateRevenueReport() {
        return ResponseEntity.ok(reportService.generateRevenueReport());
    }
}
