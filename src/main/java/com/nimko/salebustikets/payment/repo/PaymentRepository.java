package com.nimko.salebustikets.payment.repo;

import com.nimko.salebustikets.payment.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
