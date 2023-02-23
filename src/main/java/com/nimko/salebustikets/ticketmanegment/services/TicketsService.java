package com.nimko.salebustikets.ticketmanegment.services;

import com.nimko.salebustikets.ticketmanegment.dto.TicketDto;
import com.nimko.salebustikets.ticketmanegment.dto.TicketInfoDto;
import com.nimko.salebustikets.ticketmanegment.models.Flight;
import com.nimko.salebustikets.ticketmanegment.models.Ticket;
import com.nimko.salebustikets.ticketmanegment.repo.FlightsRepository;
import com.nimko.salebustikets.ticketmanegment.repo.TicketsRepository;
import com.nimko.salebustikets.utils.FlightNoExistException;
import com.nimko.salebustikets.utils.OnTheFlightNoSeatsException;
import com.nimko.salebustikets.utils.PaymentStatus;
import com.nimko.salebustikets.utils.TicketNoExistException;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class TicketsService {
  @Value("${my.env.path}")
  private String path;

  static final String PAYMENT_ENDPOINT = "/payments";

  private final TicketsRepository ticketsRepository;
  private final FlightsRepository flightsRepository;

  @Autowired
  public TicketsService(TicketsRepository ticketsRepository, FlightsRepository flightsRepository) {
    this.ticketsRepository = ticketsRepository;
    this.flightsRepository = flightsRepository;
  }

  @Transactional
  public Integer buyTicket(TicketDto dto) throws OnTheFlightNoSeatsException, FlightNoExistException {
    Ticket ticket = new Ticket();
    ticket.setFullName(dto.getFullName());
    Flight flight = flightsRepository.findById(dto.getFlightId())
        .orElseThrow(FlightNoExistException::new);
    int ticketNum = flight.getNumOfTickets();
    if (ticketNum > 0) {
      ticketNum -= 1;
      flight.setNumOfTickets(ticketNum);
      flightsRepository.save(flight);
    } else {
      throw new OnTheFlightNoSeatsException();
    }
    ticket.setFlight(flight);
    ticket = ticketsRepository.save(ticket);
    String paymentId = getFromHttpPostPayment(ticket);
    PaymentStatus status = getFromHttpGetPayment(paymentId);
    log.info("Ticket paymentId: {}; status {}",paymentId, status.name());
    ticket.setPaymentStatus(status);
    log.info("Ticket {}, Flight: {}",ticket, flightsRepository.findById(ticket.getFlight().getId()));
    return ticketsRepository.save(ticket).getTicketId();
  }

  public TicketInfoDto getInfoTicket(Integer ticketId) throws TicketNoExistException {
    Ticket ticket = ticketsRepository.findById(ticketId).orElseThrow(TicketNoExistException::new);
    log.info("Ticket - {}", ticket);
    TicketInfoDto dto = new TicketInfoDto(ticket.getFlight(),ticket.getPaymentStatus());
    return dto;
  }




  private PaymentStatus getFromHttpGetPayment(String paymentId){
    PaymentStatus status = null;
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(new URI(path + PAYMENT_ENDPOINT + "?id=" +paymentId))
          .GET()
          .build();
      status = PaymentStatus.valueOf(getResponse(request));
    } catch (URISyntaxException e) {
      log.error("URI problems {}", e.getMessage());
    }
    return status;
  }

  private String getFromHttpPostPayment(Ticket ticket) {
    log.info("Ticket {}",ticket);
    String paymentId = "";
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(new URI(path + PAYMENT_ENDPOINT))
          .header("Content-Type", "application/json")
          .POST(BodyPublishers.ofString("{\"fullName\": \""
              + ticket.getFullName() + "\", \"sum\": "
              + ticket.getFlight().getPrice() + "}"))
          .build();
      paymentId = getResponse(request);
    } catch (URISyntaxException e) {
      log.error("URI problems {}", e.getMessage());
    }
    return paymentId;
  }

  private String getResponse(HttpRequest request){
    HttpResponse<String> response = null;
    try {
      response = HttpClient
          .newBuilder()
          .build()
          .send(request, BodyHandlers.ofString());
    } catch (IOException e) {
      log.error("IOException: {}", e.getMessage());
    } catch (InterruptedException e) {
      log.error("Interrupted: {}", e.getMessage());
    }
    log.info("Response: {}", response);
    return response.body();
  }
}
