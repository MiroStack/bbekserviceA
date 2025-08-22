package com.bbek.BbekServiceA.repository;

import com.bbek.BbekServiceA.entities.OfferingPaymentRfEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferingPaymentRepo extends JpaRepository<OfferingPaymentRfEntity, Long> {
  OfferingPaymentRfEntity findByPaymentMethod(String username);
}
