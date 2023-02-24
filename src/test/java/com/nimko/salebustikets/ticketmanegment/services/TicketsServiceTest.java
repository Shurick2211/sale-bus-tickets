package com.nimko.salebustikets.ticketmanegment.services;

import com.nimko.salebustikets.ticketmanegment.dto.TicketDto;
import com.nimko.salebustikets.ticketmanegment.models.Flight;
import com.nimko.salebustikets.ticketmanegment.models.Ticket;
import com.nimko.salebustikets.ticketmanegment.repo.FlightsRepository;
import com.nimko.salebustikets.ticketmanegment.repo.TicketsRepository;
import com.nimko.salebustikets.utils.FlightNoExistException;
import com.nimko.salebustikets.utils.OnTheFlightNoSeatsException;
import com.nimko.salebustikets.utils.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class TicketsServiceTest {

    TicketsRepository ticketsRepository = Mockito.mock(TicketsRepository.class);

    FlightsRepository flightsRepository = Mockito.mock(FlightsRepository.class);

    HttpPaymentService httpPaymentService = Mockito.mock(HttpPaymentService.class);

    TicketsService ticketsService;
    Flight flight;
    List<Ticket> newTicket;
    static final int SEATS = 40;


    @BeforeEach
    void setUp() {
        ticketsService = new TicketsService(ticketsRepository,flightsRepository, httpPaymentService);
        flight = new Flight("1A","Kharkiv","Kiev",
                LocalDateTime.of(2023,3, 1,10,0),
                500, SEATS);
        newTicket = List.of(
            new Ticket(1,"I V N", flight, "123", PaymentStatus.NEW),
            new Ticket(2,"D F G", flight, "234", PaymentStatus.NEW),
            new Ticket(3,"W S D", flight, "456", PaymentStatus.NEW),
            new Ticket(4,"Q W E", flight, "567", PaymentStatus.NEW),
            new Ticket(5,"Z X C", flight, "789", PaymentStatus.NEW)
        );
        when(ticketsRepository.findAllTicketsByPaymentStatus(any())).thenReturn(newTicket);
    }

    @Test
    void buyTicket() throws OnTheFlightNoSeatsException, FlightNoExistException {
      Optional<Flight> flightOptional = Optional.of(flight);
      when(flightsRepository.findById(any())).thenReturn(flightOptional);
      when(httpPaymentService.createPayment(any())).thenReturn("123");
      when(ticketsRepository.save(any())).thenReturn(newTicket.get(0));
      assertEquals(1, ticketsService.buyTicket(new TicketDto("I V N", "1A")));
      assertEquals(SEATS - 1, flight.getNumOfTickets());
    }

    @Test
    void buyTicketFlightNoExistException(){
        assertThrows(FlightNoExistException.class,
                () ->  ticketsService.buyTicket(new TicketDto("I V N", "1A")));
    }

    @Test
    void buyTicketNoSeatsException(){
        flight.setNumOfTickets(0);
        Optional<Flight> flightOptional = Optional.of(flight);
        when(flightsRepository.findById(any())).thenReturn(flightOptional);
        when(httpPaymentService.createPayment(any())).thenReturn("123");
        when(ticketsRepository.save(any())).thenReturn(newTicket.get(0));
        assertThrows(OnTheFlightNoSeatsException.class,
                () ->  ticketsService.buyTicket(new TicketDto("I V N", "1A")));
    }


    @Test
    void checkNewWithReturnAllTickets() {
        flight.setNumOfTickets(SEATS - newTicket.size());
        when(httpPaymentService.getPaymentStatus(any())).thenReturn(PaymentStatus.FAILED);
        ticketsService.checkNew();
        assertEquals(SEATS, flight.getNumOfTickets());
    }

    @Test
    void checkNewAllTicketsDone() {
        flight.setNumOfTickets(SEATS - newTicket.size());
        when(httpPaymentService.getPaymentStatus(any())).thenReturn(PaymentStatus.DONE);
        ticketsService.checkNew();
        assertEquals(SEATS - newTicket.size(), flight.getNumOfTickets());
    }
}