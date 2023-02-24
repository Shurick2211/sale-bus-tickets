package com.nimko.salebustikets.payment.services;

import com.nimko.salebustikets.payment.models.Payment;
import com.nimko.salebustikets.payment.repo.PaymentRepository;
import com.nimko.salebustikets.utils.PaymentNoExistException;
import com.nimko.salebustikets.utils.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PaymentServiceTest {

  PaymentRepository paymentRepository = Mockito.mock(PaymentRepository.class);
  PaymentService service = new PaymentService(paymentRepository);

  @Test
  void getPaymentStatus() throws PaymentNoExistException {
    Optional<Payment> payment = Optional.of(new Payment(1, "FIO", 15.0, PaymentStatus.NEW));
    when(paymentRepository.findById(any())).thenReturn(payment);
    when(paymentRepository.save(any())).thenReturn(payment.get());
    assertInstanceOf(PaymentStatus.class, service.getPaymentStatus( 1));
  }
  @Test
  void getPaymentStatusPaymentNoExistException () {
    assertThrows(PaymentNoExistException.class, () -> service.getPaymentStatus( 1));
  }

}