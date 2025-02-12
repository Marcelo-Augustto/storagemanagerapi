package com.example.storagemanagerapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // e.g., "Stock Movement", "Best-Selling Products", "Revenue Report"
    private LocalDateTime generatedAt = LocalDateTime.now();

    @Lob
    private String data;
}

