package com.nimko.salebustikets.ticketmanegment.repo;

import com.nimko.salebustikets.ticketmanegment.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightsRepository extends JpaRepository<Flight,String> {

}
