package com.nimko.salebustikets.payment.services;

import com.nimko.salebustikets.payment.dto.PaymentDto;
import com.nimko.salebustikets.utils.PaymentStatus;
import java.time.LocalTime;
import java.util.Base64;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentService {

  public String createPayment(PaymentDto payment){
    String id = Base64.getEncoder().encodeToString(
        (LocalTime.now().getNano() + "{|}" + payment.getFullName()
            + "{|}" + payment.getSum()).getBytes());
    log.info("Create with id: {}", id);
    return id;
  }

  public PaymentStatus getPaymentStatus(String id){
    Random random = new Random();
    int indexStatus = random.nextInt(PaymentStatus.values().length);
    log.info("Return index: {}", indexStatus);
    return PaymentStatus.values()[indexStatus];
  }


}
