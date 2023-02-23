package com.nimko.salebustikets.ticketmanegment.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimko.salebustikets.ticketmanegment.dto.PaymentDto;
import com.nimko.salebustikets.ticketmanegment.models.Ticket;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HttpPaymentService {
  @Value("${my.env.path}")
  private String path;

  static final String PAYMENT_ENDPOINT = "/payments";

  public PaymentStatus getPaymentStatus(String paymentId){
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

  public String createPayment(Ticket ticket) {
    log.info("Ticket {}",ticket);
    String paymentId = "";
    ObjectMapper dtoMapper = new ObjectMapper();
    try {
      String json = dtoMapper.writeValueAsString(
          new PaymentDto(ticket.getFullName(),ticket.getFlight().getPrice()));
      HttpRequest request = HttpRequest.newBuilder()
          .uri(new URI(path + PAYMENT_ENDPOINT))
          .header("Content-Type", "application/json")
          .POST(BodyPublishers.ofString(json))
          .build();
      paymentId = getResponse(request);
    } catch (URISyntaxException | JsonProcessingException e) {
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
