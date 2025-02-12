package com.example.storagemanagerapi.repository;

import com.example.storagemanagerapi.model.Report;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("SELECT o.product.name, SUM(o.quantity) FROM OrderItem o GROUP BY o.product ORDER BY SUM(o.quantity) DESC")
    List<Object[]> findBestSellingProducts();

    @Query("SELECT SUM(o.totalAmount) FROM Order o")
    Double findTotalRevenue();
}
