package com.nimko.salebustikets.ticketmanegment.models;

import com.nimko.salebustikets.utils.PaymentStatus;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Ticket {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int ticketId;
  private String fullName;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "id")
  private Flight flight;
  private String paymentId;

  private PaymentStatus paymentStatus;

}
