package com.example.lab3.service;

import com.example.lab3.entity.CommercialOffer;
import com.example.lab3.entity.Supplier;
import com.example.lab3.repository.CommercialOfferRepository;
import com.example.lab3.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private CommercialOfferRepository offerRepository;

    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    public Optional<Supplier> getById(Long id) {
        return supplierRepository.findById(id);
    }

    public Supplier create(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier update(Long id, Supplier updatedSupplier) {
        return supplierRepository.findById(id).map(supplier -> {
            if (updatedSupplier.getName() != null) {
                supplier.setName(updatedSupplier.getName());
            }
            if (updatedSupplier.getCategory() != null) {
                supplier.setCategory(updatedSupplier.getCategory());
            }
            if (updatedSupplier.getIsApproved() != null) {
                supplier.setIsApproved(updatedSupplier.getIsApproved());
            }
            return supplierRepository.save(supplier);
        }).orElseThrow(() -> new RuntimeException("Supplier not found with id " + id));
    }

    public void delete(Long id) {
        supplierRepository.deleteById(id);
    }

    // БИЗНЕС-ОПЕРАЦИЯ №1: Блокировка недобросовестного поставщика
    @Transactional
    public Supplier blockSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Поставщик не найден"));

        if (!supplier.getIsApproved()) {
            throw new IllegalStateException("Поставщик уже заблокирован");
        }

        supplier.setIsApproved(false);
        supplierRepository.save(supplier);

        // Согласованность данных: удаляем все предложения заблокированного поставщика
        List<CommercialOffer> offers = offerRepository.findBySupplierId(id);
        offerRepository.deleteAll(offers);

        return supplier;
    }
}