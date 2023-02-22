package com.nimko.salebustikets.payment.controllers;

import com.nimko.salebustikets.payment.dto.PaymentDto;
import com.nimko.salebustikets.payment.services.PaymentService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@OpenAPIDefinition(info =
@Info(
    title = "${api.description}",
    version = "${api.ver}",
    description = "My tickets API",
    contact = @Contact(name = "Developer", email = "shurick2211@gmail.com")
)
)
public class PaymentController {

  private final PaymentService paymentService;

  @Autowired
  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping
  @Operation(summary = "Create new payment",
      description = "Создания оплаты на вход принимает фио клиента + сумма, "
          + "на выходе возвращает уникальный идентификатор платежа.")
  public ResponseEntity<String> createPayment(@RequestBody PaymentDto payment){
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(paymentService.createPayment(payment));
  }

  @GetMapping
  @Operation(summary = "Get payment status",
      description = "Получения статусов на вход принимает уникальный идентификатор платежа,"
          + " на выходе случайным образом отдает 1 из статусов NEW/FAILED/DONE")
  public ResponseEntity<String> getPayment(@RequestParam("id") String id){
    return ResponseEntity.ok(paymentService.getPaymentStatus(id).name());
  }

}
