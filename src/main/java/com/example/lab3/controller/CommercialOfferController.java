package com.example.lab3.controller;

import com.example.lab3.entity.CommercialOffer;
import com.example.lab3.service.CommercialOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commercial-offers")
public class CommercialOfferController {

    @Autowired
    private CommercialOfferService commercialOfferService;

    @GetMapping
    public List<CommercialOffer> getAllOffers() {
        return commercialOfferService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommercialOffer> getOfferById(@PathVariable Long id) {
        return commercialOfferService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<CommercialOffer> createOffer(@RequestBody CommercialOffer offer) {
        CommercialOffer created = commercialOfferService.create(offer);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommercialOffer> updateOffer(@PathVariable Long id, @RequestBody CommercialOffer offer) {
        try {
            CommercialOffer updated = commercialOfferService.update(id, offer);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable Long id) {
        commercialOfferService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Эндпоинт для бизнес-операции №2
    @PostMapping("/{id}/approve")
    public ResponseEntity<?> approveOffer(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(commercialOfferService.approveOffer(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}