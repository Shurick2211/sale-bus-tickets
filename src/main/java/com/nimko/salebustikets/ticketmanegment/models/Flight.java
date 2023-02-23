package com.nimko.salebustikets.ticketmanegment.models;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Flight {
  @Id
  private String id;
  private String start;
  private String end;
  private LocalDateTime time;
  private double price;
  private int numOfTickets;

}
