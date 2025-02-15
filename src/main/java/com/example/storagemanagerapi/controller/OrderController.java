package com.example.storagemanagerapi.controller;

import com.example.storagemanagerapi.enums.OrderStatus;
import com.example.storagemanagerapi.model.Order;
import com.example.storagemanagerapi.model.OrderItem;
import com.example.storagemanagerapi.model.User;
import com.example.storagemanagerapi.service.OrderService;
import com.example.storagemanagerapi.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order Management", description = "APIs for handling orders")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/{userId}")
    @Operation(summary = "Create an order", security = @SecurityRequirement(name = "bearerAuth"), description = "Creates an order for a given user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "404", description = "User or Product not found"),
            @ApiResponse(responseCode = "400", description = "Insufficient stock")
    })
    public ResponseEntity<Order> createOrder(@PathVariable Long userId, @RequestBody List<OrderItem> items) {
        User user = userService.getUserById(userId);
        Order order = orderService.createOrder(user, items);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @PutMapping("/{orderId}/status")
    @Operation(summary = "Update order status", security = @SecurityRequirement(name = "bearerAuth"), description = "Updates the status of an order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus status) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user orders", security = @SecurityRequirement(name = "bearerAuth"), description = "Retrieves all orders for a given user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User orders retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(orderService.getUserOrders(user));
    }

    @GetMapping("/details/{orderId}")
    @Operation(summary = "Get order details", security = @SecurityRequirement(name = "bearerAuth"), description = "Fetches details of a specific order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "Cancel order", security = @SecurityRequirement(name = "bearerAuth"), description = "Cancels an order")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Order cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
