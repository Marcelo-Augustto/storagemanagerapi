package com.example.storagemanagerapi.repository;

import com.example.storagemanagerapi.model.Order;
import com.example.storagemanagerapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
