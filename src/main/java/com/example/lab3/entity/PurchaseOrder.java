package com.example.lab3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchase_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "purchase_request_id", nullable = false)
    private PurchaseRequest purchaseRequest; // Заявка на закупку

    @ManyToOne
    @JoinColumn(name = "commercial_offer_id", nullable = false)
    private CommercialOffer commercialOffer; // Выбранное коммерческое предложение

    @Column(nullable = false)
    private String status; // Статус исполнения (например, "CREATED", "PAID", "COMPLETED")
}