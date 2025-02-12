package com.example.storagemanagerapi.repository;

import com.example.storagemanagerapi.model.Order;
import com.example.storagemanagerapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    @Query("SELECT oi.product.name, SUM(oi.quantity) " +
            "FROM OrderItem oi " +
            "GROUP BY oi.product " +
            "ORDER BY SUM(oi.quantity) DESC")
    List<Object[]> findBestSellingProducts();

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o")
    Double findTotalRevenue();
}
