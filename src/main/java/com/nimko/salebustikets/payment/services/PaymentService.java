package com.nimko.salebustikets.payment.services;

import com.nimko.salebustikets.payment.dto.PaymentDto;
import com.nimko.salebustikets.payment.models.Payment;
import com.nimko.salebustikets.payment.repo.PaymentRepository;
import com.nimko.salebustikets.utils.PaymentNoExistException;
import com.nimko.salebustikets.utils.PaymentStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class PaymentService {

  private final PaymentRepository paymentRepository;

  @Autowired
  public PaymentService(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  public long createPayment(PaymentDto paymentDto){
    long id =  paymentRepository.save(new Payment(paymentDto.getFullName(),paymentDto.getSum())).getId();
    log.info("Create new Payment with id: {}", id);
    return id;
  }

  public PaymentStatus getPaymentStatus(long id) throws PaymentNoExistException {
    Payment payment = paymentRepository.findById(id).orElseThrow(PaymentNoExistException::new);
    Random random = new Random();
    int indexStatus = random.nextInt(PaymentStatus.values().length);
    payment.setStatus(PaymentStatus.values()[indexStatus]);
    log.info("Return payment: {}", payment);
    return paymentRepository.save(payment).getStatus();
  }

}
