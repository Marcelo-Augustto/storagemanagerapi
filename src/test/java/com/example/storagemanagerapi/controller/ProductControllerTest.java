package com.example.storagemanagerapi.controller;

import com.example.storagemanagerapi.model.Product;
import com.example.storagemanagerapi.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.storagemanagerapi.model.Stock;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Product product;
    private Stock stock;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        product = new Product(1L, "Laptop", 1500.00, "Gaming laptop with RTX 4070", "Electronics", new Stock(1L, product, 10, 2));
    }

    @Test
    @Order(1)
    void testCreateProduct() throws Exception {
        when(productService.createProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Laptop\",\"price\":1500.00,\"description\":\"Gaming laptop with RTX 4070\",\"category\":\"Electronics\",\"stock\":{\"quantity\":10,\"minimumStockLevel\":2}}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.category").value("Electronics"))
                .andExpect(jsonPath("$.stock.quantity").value(10));

        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    @Order(2)
    void testGetProductById() throws Exception {
        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.category").value("Electronics"))
                .andExpect(jsonPath("$.stock.quantity").value(10));

        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    @Order(3)
    void testUpdateProduct() throws Exception {
        Product updatedProduct = new Product(1L, "Gaming Laptop", 1800.00, "Updated laptop description", "Electronics", new Stock(1L, product, 15, 3));
        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Gaming Laptop\",\"price\":1800.00,\"description\":\"Updated laptop description\",\"category\":\"Electronics\",\"stock\":{\"quantity\":15,\"minimumStockLevel\":3}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gaming Laptop"))
                .andExpect(jsonPath("$.price").value(1800.00))
                .andExpect(jsonPath("$.stock.quantity").value(15));

        verify(productService, times(1)).updateProduct(eq(1L), any(Product.class));
    }

    @Test
    @Order(4)
    void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(1L);
    }
}