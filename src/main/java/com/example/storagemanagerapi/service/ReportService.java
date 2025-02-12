package com.example.storagemanagerapi.service;

import com.example.storagemanagerapi.model.Report;
import com.example.storagemanagerapi.model.Stock;
import com.example.storagemanagerapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Report generateStockMovementReport() {
        List<Stock> stockList = stockRepository.findAll();
        String data = stockList.stream()
                .map(stock -> stock.getProduct().getName() + " - Quantity: " + stock.getQuantity())
                .collect(Collectors.joining("\n"));

        Report report = new Report(null, "Stock Movement Report", LocalDateTime.now(), data);
        return reportRepository.save(report);
    }

    public Report generateBestSellingProductsReport() {
        List<Object[]> results = orderRepository.findBestSellingProducts();
        String data = results.stream()
                .map(r -> "Product: " + r[0] + " - Sold: " + r[1])
                .collect(Collectors.joining("\n"));

        Report report = new Report(null, "Best-Selling Products Report", LocalDateTime.now(), data);
        return reportRepository.save(report);
    }

    public Report generateRevenueReport() {
        double totalRevenue = orderRepository.findTotalRevenue();
        String data = "Total Revenue: $" + totalRevenue;

        Report report = new Report(null, "Revenue Report", LocalDateTime.now(), data);
        return reportRepository.save(report);
    }
}
