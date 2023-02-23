package com.nimko.salebustikets.ticketmanegment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Schema(name = "Схема покупки ticket", description = "DTO для фронтенда!")
public class TicketDto {
  private String fullName;
  private String flightId;

}
