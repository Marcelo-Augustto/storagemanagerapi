package com.example.storagemanagerapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Stock stock;
}
