package com.example.lab3.service;

import com.example.lab3.entity.PurchaseRequest;
import com.example.lab3.repository.PurchaseRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseRequestService {

    @Autowired
    private PurchaseRequestRepository requestRepository;

    public List<PurchaseRequest> getAll() {
        return requestRepository.findAll();
    }

    public Optional<PurchaseRequest> getById(Long id) {
        return requestRepository.findById(id);
    }

    public PurchaseRequest create(PurchaseRequest request) {
        return requestRepository.save(request);
    }

    public PurchaseRequest update(Long id, PurchaseRequest updatedRequest) {
        return requestRepository.findById(id).map(request -> {
            if (updatedRequest.getDescription() != null) request.setDescription(updatedRequest.getDescription());
            if (updatedRequest.getLimitAmount() != null) request.setLimitAmount(updatedRequest.getLimitAmount());
            if (updatedRequest.getStatus() != null) request.setStatus(updatedRequest.getStatus());
            if (updatedRequest.getAuthor() != null) request.setAuthor(updatedRequest.getAuthor());
            return requestRepository.save(request);
        }).orElseThrow(() -> new RuntimeException("Purchase request not found with id " + id));
    }

    public void delete(Long id) {
        requestRepository.deleteById(id);
    }

    // БИЗНЕС-ОПЕРАЦИЯ №3: Отмена заявки
    @Transactional
    public PurchaseRequest cancelRequest(Long id) {
        PurchaseRequest request = requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Заявка не найдена"));

        if (!"NEW".equals(request.getStatus())) {
            throw new IllegalStateException("Нельзя отменить заявку, по которой уже идет работа (статус: " + request.getStatus() + ")");
        }

        request.setStatus("CANCELLED");
        return requestRepository.save(request);
    }
}