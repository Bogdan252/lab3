package com.example.lab3.controller;

import com.example.lab3.entity.PurchaseOrder;
import com.example.lab3.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService orderService;

    @GetMapping
    public List<PurchaseOrder> getAllOrders() {
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrder> getOrderById(@PathVariable Long id) {
        return orderService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<PurchaseOrder> createOrder(@RequestBody PurchaseOrder order) {
        PurchaseOrder created = orderService.create(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PurchaseOrder> updateOrder(@PathVariable Long id, @RequestBody PurchaseOrder order) {
        try {
            PurchaseOrder updated = orderService.update(id, order);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Эндпоинт для бизнес-операции №4
    @PostMapping("/{id}/pay")
    public ResponseEntity<?> payOrder(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(orderService.payOrder(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Эндпоинт для бизнес-операции №5
    @PostMapping("/{id}/complete")
    public ResponseEntity<?> completeOrder(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(orderService.completeOrder(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}