package com.example.lab3.controller;

import com.example.lab3.entity.Supplier;
import com.example.lab3.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public List<Supplier> getAllSuppliers() {
        return supplierService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {
        return supplierService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier) {
        Supplier createdSupplier = supplierService.create(supplier);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSupplier);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Long id, @RequestBody Supplier supplier) {
        try {
            Supplier updatedSupplier = supplierService.update(id, supplier);
            return ResponseEntity.ok(updatedSupplier);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Эндпоинт для бизнес-операции №1
    @PostMapping("/{id}/block")
    public ResponseEntity<?> blockSupplier(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(supplierService.blockSupplier(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}