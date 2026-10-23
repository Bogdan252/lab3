package com.example.lab3.service;

import com.example.lab3.entity.CommercialOffer;
import com.example.lab3.entity.PurchaseOrder;
import com.example.lab3.entity.PurchaseRequest;
import com.example.lab3.repository.CommercialOfferRepository;
import com.example.lab3.repository.PurchaseOrderRepository;
import com.example.lab3.repository.PurchaseRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommercialOfferService {

    @Autowired
    private CommercialOfferRepository offerRepository;

    @Autowired
    private PurchaseRequestRepository requestRepository;

    @Autowired
    private PurchaseOrderRepository orderRepository;

    public List<CommercialOffer> getAll() {
        return offerRepository.findAll();
    }

    public Optional<CommercialOffer> getById(Long id) {
        return offerRepository.findById(id);
    }

    public CommercialOffer create(CommercialOffer offer) {
        return offerRepository.save(offer);
    }

    public CommercialOffer update(Long id, CommercialOffer updatedOffer) {
        return offerRepository.findById(id).map(offer -> {
            if (updatedOffer.getPrice() != null) offer.setPrice(updatedOffer.getPrice());
            if (updatedOffer.getDeliveryTerm() != null) offer.setDeliveryTerm(updatedOffer.getDeliveryTerm());
            if (updatedOffer.getValidityPeriod() != null) offer.setValidityPeriod(updatedOffer.getValidityPeriod());
            if (updatedOffer.getPurchaseRequest() != null) offer.setPurchaseRequest(updatedOffer.getPurchaseRequest());
            if (updatedOffer.getSupplier() != null) offer.setSupplier(updatedOffer.getSupplier());
            return offerRepository.save(offer);
        }).orElseThrow(() -> new RuntimeException("Commercial offer not found with id " + id));
    }

    public void delete(Long id) {
        offerRepository.deleteById(id);
    }

    // БИЗНЕС-ОПЕРАЦИЯ №2: Утверждение коммерческого предложения с автосозданием заказа
    @Transactional
    public PurchaseOrder approveOffer(Long offerId) {
        CommercialOffer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Предложение не найдено"));

        PurchaseRequest request = offer.getPurchaseRequest();

        if (!"NEW".equals(request.getStatus())) {
            throw new IllegalStateException("Утвердить предложение можно только для новой заявки");
        }

        request.setStatus("APPROVED");
        requestRepository.save(request);

        PurchaseOrder order = new PurchaseOrder();
        order.setPurchaseRequest(request);
        order.setCommercialOffer(offer);
        order.setStatus("CREATED");

        return orderRepository.save(order);
    }
}