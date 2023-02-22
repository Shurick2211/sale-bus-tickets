package com.nimko.salebustikets.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentDto {
  private String fullName;
  private double sum;

}
