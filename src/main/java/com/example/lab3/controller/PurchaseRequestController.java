package com.example.lab3.controller;

import com.example.lab3.entity.PurchaseRequest;
import com.example.lab3.service.PurchaseRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-requests")
public class PurchaseRequestController {

    @Autowired
    private PurchaseRequestService requestService;

    @GetMapping
    public List<PurchaseRequest> getAllRequests() {
        return requestService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseRequest> getRequestById(@PathVariable Long id) {
        return requestService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<PurchaseRequest> createRequest(@RequestBody PurchaseRequest request) {
        PurchaseRequest created = requestService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PurchaseRequest> updateRequest(@PathVariable Long id, @RequestBody PurchaseRequest request) {
        try {
            PurchaseRequest updated = requestService.update(id, request);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        requestService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Эндпоинт для бизнес-операции №3
    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelRequest(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(requestService.cancelRequest(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}