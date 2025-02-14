package com.example.storagemanagerapi.controller;

import com.example.storagemanagerapi.model.Product;
import com.example.storagemanagerapi.service.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "API for managing products")
@SecurityRequirement(name = "bearerAuth")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get all products", description = "Fetches a list of all products available in the system")
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(summary = "Get product by ID", description = "Retrieves details of a product using its unique ID")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(
            @Parameter(description = "ID of the product to be retrieved") @PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(summary = "Create a new product", description = "Adds a new product to the system")
    @PostMapping
    public ResponseEntity<Product> createProduct(
            @Parameter(description = "Product details to be created") @RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
    }

    @Operation(summary = "Update an existing product", description = "Updates an existing product's details using its ID")
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @Parameter(description = "ID of the product to update") @PathVariable Long id,
            @Parameter(description = "Updated product details") @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @Operation(summary = "Delete a product", description = "Removes a product from the system using its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID of the product to be deleted") @PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
