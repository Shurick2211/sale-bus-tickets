package com.nimko.salebustikets.ticketmanegment.models;

import com.nimko.salebustikets.utils.PaymentStatus;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ticket {
  @Id
  private Integer ticketId;
  private String fullName;
  @ManyToOne(fetch = FetchType.LAZY)
  private Flight flight;
  private PaymentStatus paymentStatus;

}
