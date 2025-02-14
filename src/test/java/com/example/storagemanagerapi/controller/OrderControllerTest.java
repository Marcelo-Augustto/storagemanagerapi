package com.example.storagemanagerapi.controller;

import com.example.storagemanagerapi.auth.JwtUtil;
import com.example.storagemanagerapi.enums.OrderStatus;
import com.example.storagemanagerapi.model.*;
import com.example.storagemanagerapi.repository.ProductRepository;
import com.example.storagemanagerapi.repository.StockRepository;
import com.example.storagemanagerapi.service.OrderService;
import com.example.storagemanagerapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private ProductRepository productRepository;

    @MockitoBean
    private StockRepository stockRepository;

    private User user;
    private Product product;
    private Stock stock;
    private Order order;
    private List<OrderItem> orderItems;
    private String token;

    @BeforeEach
    public void setup() {
        user = new User(1L, "john_doe", "password123", "john.doe@example.com", "USER");

        stock = new Stock(1L, product, 100, 10);  // The minimum stock level is set to 10

        product = new Product(1L, "Product 1", 10.0, "Product Description", "Category 1", stock);

        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(2);
        item.setPrice(product.getPrice());
        item.setProductId(product.getId());

        orderItems = new ArrayList<>();
        orderItems.add(item);

        order = new Order(1L, user, orderItems, OrderStatus.PENDING, LocalDateTime.now(), 20.0);

        token = "Bearer " + JwtUtil.generateToken(user.getUsername());
    }

    @Test
    public void testCreateOrder() throws Exception {
        when(userService.getUserById(1L)).thenReturn(user);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(stockRepository.findByProductId(1L)).thenReturn(Optional.of(stock));
        when(orderService.createOrder(eq(user), anyList())).thenReturn(order);

        mockMvc.perform(post("/api/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token) // Include token
                        .content("[{\"productId\": 1, \"quantity\": 2}]"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.totalAmount").value(20.0))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    public void testUpdateOrderStatus() throws Exception {
        Order updatedOrder = new Order(1L, user, orderItems, OrderStatus.SHIPPED, LocalDateTime.now(), 20.0);
        when(orderService.updateOrderStatus(1L, OrderStatus.SHIPPED)).thenReturn(updatedOrder);

        mockMvc.perform(put("/api/orders/1/status")
                        .param("status", "SHIPPED")
                        .header("Authorization", token)) // Include token
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SHIPPED"));
    }

    @Test
    public void testGetUserOrders() throws Exception {
        when(userService.getUserById(1L)).thenReturn(user);
        when(orderService.getUserOrders(user)).thenReturn(Collections.singletonList(order));

        mockMvc.perform(get("/api/orders/1")
                        .header("Authorization", token)) // Include token
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].totalAmount").value(20.0));
    }

    @Test
    public void testGetOrderById() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(order);

        mockMvc.perform(get("/api/orders/details/1")
                        .header("Authorization", token)) // Include token
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.totalAmount").value(20.0));
    }

    @Test
    public void testCancelOrder() throws Exception {
        doNothing().when(orderService).cancelOrder(1L);

        mockMvc.perform(delete("/api/orders/1")
                        .header("Authorization", token)) // Include token
                .andExpect(status().isNoContent());
    }
}
