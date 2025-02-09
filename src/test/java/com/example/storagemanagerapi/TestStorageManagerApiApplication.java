package com.example.storagemanagerapi;

import org.springframework.boot.SpringApplication;

public class TestStorageManagerApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(StorageManagerApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
