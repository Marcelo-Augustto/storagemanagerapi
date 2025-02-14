package com.example.storagemanagerapi.exception.product;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String name) {
        super("Product with name '" + name + "' already exists");
    }
}
