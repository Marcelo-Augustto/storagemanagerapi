package com.example.storagemanagerapi.service;

import com.example.storagemanagerapi.exception.report.DataUnavailableException;
import com.example.storagemanagerapi.exception.report.ReportNotFoundException;
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
        List<Report> reports = reportRepository.findAll();
        if (reports.isEmpty()) {
            throw new ReportNotFoundException(0L); // Using 0L as placeholder, since this is for all reports
        }
        return reports;
    }

    public Report generateStockMovementReport() {
        List<Stock> stockList = stockRepository.findAll();
        if (stockList.isEmpty()) {
            throw new DataUnavailableException("Stock Movement Report");
        }

        String data = stockList.stream()
                .map(stock -> stock.getProduct().getName() + " - Quantity: " + stock.getQuantity())
                .collect(Collectors.joining("\n"));

        Report report = new Report(null, "Stock Movement Report", LocalDateTime.now(), data);
        return reportRepository.save(report);
    }

    public Report generateBestSellingProductsReport() {
        List<Object[]> results = orderRepository.findBestSellingProducts();
        if (results.isEmpty()) {
            throw new DataUnavailableException("Best-Selling Products Report");
        }

        String data = results.stream()
                .map(r -> "Product: " + r[0] + " - Sold: " + r[1])
                .collect(Collectors.joining("\n"));

        Report report = new Report(null, "Best-Selling Products Report", LocalDateTime.now(), data);
        return reportRepository.save(report);
    }

    public Report generateRevenueReport() {
        Double totalRevenue = orderRepository.findTotalRevenue();
        if (totalRevenue == null || totalRevenue <= 0) {
            throw new DataUnavailableException("Revenue Report");
        }

        String data = "Total Revenue: $" + totalRevenue;

        Report report = new Report(null, "Revenue Report", LocalDateTime.now(), data);
        return reportRepository.save(report);
    }
}
