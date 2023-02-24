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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class TicketsService {

  private final TicketsRepository ticketsRepository;
  private final FlightsRepository flightsRepository;
  private final HttpPaymentService httpPaymentService;

  @Autowired
  public TicketsService(TicketsRepository ticketsRepository, FlightsRepository flightsRepository,
      HttpPaymentService httpPaymentService) {
    this.ticketsRepository = ticketsRepository;
    this.flightsRepository = flightsRepository;
    this.httpPaymentService = httpPaymentService;
  }

  @Transactional
  public Integer buyTicket(TicketDto dto) throws OnTheFlightNoSeatsException, FlightNoExistException {
    Ticket ticket = new Ticket();
    ticket.setFullName(dto.getFullName());
    Flight flight = flightsRepository.findById(dto.getFlightId())
        .orElseThrow(FlightNoExistException::new);
    int ticketNum = flight.getNumOfTickets();
    if (ticketNum > 0) {
      ticketNum--;
      flight.setNumOfTickets(ticketNum);
      flightsRepository.save(flight);
    } else {
      throw new OnTheFlightNoSeatsException();
    }
    ticket.setFlight(flight);
    ticket = ticketsRepository.save(ticket);
    String paymentId = httpPaymentService.createPayment(ticket);
    ticket.setPaymentId(paymentId);
    log.info("Ticket paymentId: {}",paymentId);
    ticket.setPaymentStatus(PaymentStatus.NEW);
    log.info("Ticket {}, for check Flight: {}",ticket, flightsRepository.findById(ticket.getFlight().getId()));
    return ticketsRepository.save(ticket).getTicketId();
  }

  public TicketInfoDto getInfoTicket(Integer ticketId) throws TicketNoExistException {
    Ticket ticket = ticketsRepository.findById(ticketId).orElseThrow(TicketNoExistException::new);
    log.info("Ticket - {}", ticket);
    return new TicketInfoDto(ticket.getFlight(),ticket.getPaymentStatus());
  }


  @Scheduled(fixedDelayString =  "${check.status.delay}")
  @Transactional
  public void checkNew(){
    List<Ticket> newTicket = ticketsRepository.findAllTicketsByPaymentStatus(PaymentStatus.NEW);
    if (newTicket.isEmpty()) {
      log.info("List new tickets is empty");
      return;
    }
    log.info("List new tickets size {}" , newTicket.size());
    newTicket.forEach(ticket -> {
      ticket.setPaymentStatus(httpPaymentService.getPaymentStatus(ticket.getPaymentId()));
      ticketsRepository.save(ticket);
    });
    newTicket.stream()
        .filter(ticket -> ticket.getPaymentStatus() == PaymentStatus.FAILED)
        .forEach(ticket -> {
          Flight flight = ticket.getFlight();
          log.info("Seat RETURNED for ticket id: {}", ticket.getTicketId());
          int num = flight.getNumOfTickets();
          num++;
          flight.setNumOfTickets(num);
          flightsRepository.save(flight);
          log.info("Seats of flight {} for sale", flight.getNumOfTickets());
        });
  }
}
