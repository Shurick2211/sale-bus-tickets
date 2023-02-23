package com.nimko.salebustikets.ticketmanegment.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "Схема информации про Flight", description = "Entity в базе данных")
public class Flight {
  @Id
  private String id;
  private String start;
  private String end;
  private LocalDateTime time;
  private double price;

  private int numOfTickets;

  @JsonProperty(value = "num_of_tickets")
  public int getNumOfTickets() {
    return numOfTickets;
  }
}
