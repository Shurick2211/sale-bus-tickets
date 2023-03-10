package com.nimko.salebustikets.ticketmanegment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Schema(name = "Схема покупки ticket", description = "DTO для фронтенда!")
public class TicketDto {
  private String fullName;
  private String flightId;

  @JsonProperty(value = "full_name")
  public String getFullName() {
    return fullName;
  }

  @JsonProperty(value = "flight_id")
  public String getFlightId() {
    return flightId;
  }
}
