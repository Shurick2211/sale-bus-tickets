package com.nimko.salebustikets.ticketmanegment.services;

import com.nimko.salebustikets.ticketmanegment.dto.TicketDto;
import com.nimko.salebustikets.ticketmanegment.dto.TicketInfoDto;
import com.nimko.salebustikets.ticketmanegment.models.Ticket;
import com.nimko.salebustikets.ticketmanegment.repo.FlightsRepository;
import com.nimko.salebustikets.ticketmanegment.repo.TicketsRepository;
import com.nimko.salebustikets.utils.PaymentStatus;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketsService {

  private final TicketsRepository ticketsRepository;
  private final FlightsRepository flightsRepository;

  @Autowired
  public TicketsService(TicketsRepository ticketsRepository, FlightsRepository flightsRepository) {
    this.ticketsRepository = ticketsRepository;
    this.flightsRepository = flightsRepository;
  }

  public Integer buyTicket(TicketDto dto){
    return 0;
  }

  public TicketInfoDto getInfoTicket(Integer ticketId){
    Ticket ticket = ticketsRepository.findById(ticketId).orElseThrow();

    HttpResponse<String> response = null;
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(new URI("http://localhost:8080/payments"))
          .header("Content-Type", "application/json")
          .POST(BodyPublishers.ofString("{\"fullName\": \""
              + ticket.getFullName() + "\", \"sum\": "
              + ticket.getFlight().getPrice() + "}"))
          .build();
       response = HttpClient
          .newBuilder()
          .build()
          .send(request, BodyHandlers.ofString());
    } catch (IOException e) {
      log.error("IOException: {}", e.getMessage());
    } catch (InterruptedException e) {
      log.error("Interrupted: {}", e.getMessage());
    } catch (URISyntaxException e) {
      log.error("URI problems {}", e.getMessage());
    }
    log.info("Response: {}", response);
    PaymentStatus status = null;
    return new TicketInfoDto(ticket.getFlight(),status);
  }

}
