package com.example.lab3.repository;

import com.example.lab3.entity.CommercialOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface CommercialOfferRepository extends JpaRepository<CommercialOffer, Long> {
    List<CommercialOffer> findBySupplierId(Long supplierId);
}