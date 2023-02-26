package com.nimko.salebustikets.ticketmanegment.controllers;

import com.nimko.salebustikets.ticketmanegment.dto.TicketDto;
import com.nimko.salebustikets.ticketmanegment.dto.TicketInfoDto;
import com.nimko.salebustikets.ticketmanegment.services.TicketsService;
import com.nimko.salebustikets.utils.FlightNoExistException;
import com.nimko.salebustikets.utils.OnTheFlightNoSeatsException;
import com.nimko.salebustikets.utils.TicketNoExistException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class TicketsController {
  private final TicketsService ticketsService;

  @Autowired
  public TicketsController(TicketsService ticketsService) {
    this.ticketsService = ticketsService;
  }

  @PostMapping
  @Operation(summary = "Buy new Ticket",
      description = "Покупка билета. На вход: ФИО/ идентификатор рейса. "
          + "В базе есть три рейса с id: \"1A\", \"2F\", \"3C\""
          + "На выходе: идентификатор билета. "
          + "Передаем данные в body запроса в виде json: "
          + "{\"fullName\": \"....\", \"flightId\": \"....\"}")
  public ResponseEntity<?> buyTicket(@RequestBody TicketDto dto)
      throws OnTheFlightNoSeatsException, FlightNoExistException {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ticketsService.buyTicket(dto));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get payment status",
      description = "Получения информации по билету. На вход: id билета, "
          + "на выходе Информация о рейсе и статус платежа"
          + "Id передаем как PathVariable запрос: 'http:/...../tickets/{id}'")
  public ResponseEntity<TicketInfoDto> getTicketInfo(@PathVariable Integer id)
      throws TicketNoExistException {
    log.info("Ticket id = {}", id);
    return ResponseEntity.ok(ticketsService.getInfoTicket(id));
  }

}
