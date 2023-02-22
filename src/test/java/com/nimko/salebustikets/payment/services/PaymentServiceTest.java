package com.nimko.salebustikets.payment.services;

import static org.junit.jupiter.api.Assertions.*;

import com.nimko.salebustikets.payment.dto.PaymentDto;
import com.nimko.salebustikets.utils.PaymentStatus;
import org.junit.jupiter.api.Test;

class PaymentServiceTest {
  PaymentService service = new PaymentService();

  @Test
  void createPayment() {
    var dto = new PaymentDto("Иванов Иван Иванович", 20.05);
    assertNotEquals(service.createPayment(dto),service.createPayment(dto));
  }

  @Test
  void getPaymentStatus() {
    assertInstanceOf(PaymentStatus.class, service.getPaymentStatus(""));
  }
}