package com.nimko.salebustikets.ticketmanegment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Schema(name = "Схема создания payment", description = "DTO для фронтенда!")
public class PaymentDto {
  private String fullName;
  private double sum;

  @JsonProperty(value = "full_name")
  public String getFullName() {
    return fullName;
  }

  public double getSum() {
    return sum;
  }
}
