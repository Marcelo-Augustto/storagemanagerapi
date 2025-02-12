package com.example.storagemanagerapi.controller;

import com.example.storagemanagerapi.model.Report;
import com.example.storagemanagerapi.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @PostMapping("/stock-movement")
    public ResponseEntity<Report> generateStockMovementReport() {
        return ResponseEntity.ok(reportService.generateStockMovementReport());
    }

    @PostMapping("/best-selling")
    public ResponseEntity<Report> generateBestSellingProductsReport() {
        return ResponseEntity.ok(reportService.generateBestSellingProductsReport());
    }

    @PostMapping("/revenue")
    public ResponseEntity<Report> generateRevenueReport() {
        return ResponseEntity.ok(reportService.generateRevenueReport());
    }
}
