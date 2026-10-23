package com.example.lab3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchase_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author; // Автор заявки (ссылается на User)

    @Column(nullable = false)
    private String description; // Описание потребности

    @Column(nullable = false)
    private Double limitAmount; // Предельная сумма

    private String status; // Статус заявки (например, "NEW", "APPROVED")
}