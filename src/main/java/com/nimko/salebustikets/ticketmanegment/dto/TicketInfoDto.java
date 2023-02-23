package com.nimko.salebustikets.ticketmanegment.dto;

import com.nimko.salebustikets.ticketmanegment.models.Flight;
import com.nimko.salebustikets.utils.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Schema(name = "Схема информации про ticket", description = "DTO для фронтенда!")
public class TicketInfoDto {
  private Flight flight;
  private PaymentStatus status;

}
