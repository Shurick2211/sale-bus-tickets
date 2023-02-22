package com.nimko.salebustikets.ticketmanegment.dto;

import com.nimko.salebustikets.ticketmanegment.models.Flight;
import com.nimko.salebustikets.utils.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TicketInfoDto {
  private Flight flight;
  private PaymentStatus paymentStatus;

}
