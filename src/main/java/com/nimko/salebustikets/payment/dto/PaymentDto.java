package com.nimko.salebustikets.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(name = "Схема создания payment", description = "DTO для фронтенда!")
public class PaymentDto {
  private String fullName;
  private double sum;

}
