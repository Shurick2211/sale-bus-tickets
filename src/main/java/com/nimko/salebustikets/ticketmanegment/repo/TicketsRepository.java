package com.nimko.salebustikets.ticketmanegment.repo;

import com.nimko.salebustikets.ticketmanegment.models.Ticket;
import com.nimko.salebustikets.utils.PaymentStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketsRepository extends JpaRepository<Ticket, Integer> {

  List<Ticket> findAllTicketsByPaymentStatus(PaymentStatus status);

}
