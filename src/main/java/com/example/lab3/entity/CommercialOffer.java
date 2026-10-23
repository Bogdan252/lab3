package com.example.lab3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "commercial_offers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommercialOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "purchase_request_id", nullable = false)
    private PurchaseRequest purchaseRequest; // Заявка на закупку

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier; // Поставщик

    @Column(nullable = false)
    private Double price; // Цена

    private String deliveryTerm; // Срок поставки

    private String validityPeriod; // Срок действия
}