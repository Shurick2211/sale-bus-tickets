package com.nimko.salebustikets.ticketmanegment.controllers;

import com.nimko.salebustikets.ticketmanegment.dto.TicketDto;
import com.nimko.salebustikets.ticketmanegment.dto.TicketInfoDto;
import com.nimko.salebustikets.ticketmanegment.services.TicketsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketsController {
  private final TicketsService ticketsService;

  @Autowired
  public TicketsController(TicketsService ticketsService) {
    this.ticketsService = ticketsService;
  }

  @PostMapping
  @Operation(summary = "Buy new Ticket",
      description = "Покупка билета. На вход: ФИО/ идентификатор рейса."
          + "Передаем данные в body запроса в виде json: "
          + "{\"fullName\": \"....\", \"flightId\": \"....\"}")
  public ResponseEntity<?> buyTicket(@RequestBody TicketDto dto){
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ticketsService.buyTicket(dto));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get payment status",
      description = "Получения информации по билету. На вход: id билета, "
          + "на выходе Информация о рейсе и статус платежа"
          + "Id передаем как PathVariable запрос: 'http:/...../{id}'")
  public ResponseEntity<TicketInfoDto> getTicketInfo(@PathVariable("id") Integer ticketId) {
    return ResponseEntity.ok(ticketsService.getInfoTicket(ticketId));
  }

}
