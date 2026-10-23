package com.example.lab3.service;

import com.example.lab3.entity.PurchaseOrder;
import com.example.lab3.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository orderRepository;

    public List<PurchaseOrder> getAll() {
        return orderRepository.findAll();
    }

    public Optional<PurchaseOrder> getById(Long id) {
        return orderRepository.findById(id);
    }

    public PurchaseOrder create(PurchaseOrder order) {
        return orderRepository.save(order);
    }

    public PurchaseOrder update(Long id, PurchaseOrder updatedOrder) {
        return orderRepository.findById(id).map(order -> {
            if (updatedOrder.getStatus() != null) order.setStatus(updatedOrder.getStatus());
            if (updatedOrder.getPurchaseRequest() != null) order.setPurchaseRequest(updatedOrder.getPurchaseRequest());
            if (updatedOrder.getCommercialOffer() != null) order.setCommercialOffer(updatedOrder.getCommercialOffer());
            return orderRepository.save(order);
        }).orElseThrow(() -> new RuntimeException("Purchase order not found with id " + id));
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    // БИЗНЕС-ОПЕРАЦИЯ №4: Оплата заказа
    @Transactional
    public PurchaseOrder payOrder(Long id) {
        PurchaseOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));

        if (!"CREATED".equals(order.getStatus())) {
            throw new IllegalStateException("Оплатить можно только сформированный заказ (статус CREATED)");
        }

        order.setStatus("PAID");
        return orderRepository.save(order);
    }

    // БИЗНЕС-ОПЕРАЦИЯ №5: Завершение заказа
    @Transactional
    public PurchaseOrder completeOrder(Long id) {
        PurchaseOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));

        if (!"PAID".equals(order.getStatus())) {
            throw new IllegalStateException("Завершить поставку можно только после оплаты (статус PAID)");
        }

        order.setStatus("COMPLETED");
        return orderRepository.save(order);
    }
}