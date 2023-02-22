package com.nimko.salebustikets.ticketmanegment.repo;

import com.nimko.salebustikets.ticketmanegment.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketsRepository extends JpaRepository<Ticket, Integer> {

}
