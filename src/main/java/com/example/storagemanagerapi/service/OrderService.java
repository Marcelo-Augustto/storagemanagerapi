package com.example.storagemanagerapi.service;

import com.example.storagemanagerapi.enums.OrderStatus;
import com.example.storagemanagerapi.exception.stock.InsufficientStockException;
import com.example.storagemanagerapi.exception.order.OrderNotFoundException;
import com.example.storagemanagerapi.exception.product.ProductNotFoundException;
import com.example.storagemanagerapi.exception.stock.StockNotFoundException;
import com.example.storagemanagerapi.model.*;
import com.example.storagemanagerapi.repository.OrderRepository;
import com.example.storagemanagerapi.repository.ProductRepository;
import com.example.storagemanagerapi.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    public Order createOrder(User user, List<OrderItem> items) {
        Order order = new Order();
        order.setUser(user);
        order.setItems(new ArrayList<>());

        for (OrderItem item : items) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(item.getProductId()));

            Stock stock = stockRepository.findByProductId(product.getId())
                    .orElseThrow(() -> new StockNotFoundException(product.getId()));

            if (stock.getQuantity() < item.getQuantity()) {
                throw new InsufficientStockException(product.getName());
            }

            stock.setQuantity(stock.getQuantity() - item.getQuantity());
            stockRepository.save(stock);

            item.setProduct(product);
            item.setPrice(product.getPrice());
            item.setOrder(order);
            order.getItems().add(item);
        }

        order.calculateTotalAmount();
        return orderRepository.save(order);
    }

    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        order.setStatus(status);
        return orderRepository.save(order);
    }

    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUser(user);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public void cancelOrder(Long orderId) {
        Order order = getOrderById(orderId);
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }
}
