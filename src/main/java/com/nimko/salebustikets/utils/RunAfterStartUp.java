package com.nimko.salebustikets.utils;

import com.nimko.salebustikets.ticketmanegment.models.Flight;
import com.nimko.salebustikets.ticketmanegment.repo.FlightsRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RunAfterStartUp {
  @Autowired
  FlightsRepository flightsRepository;

  @EventListener(ApplicationReadyEvent.class)
  public void runAfterStartup() {
    Flight first = new Flight("1A","Kharkiv","Kiev",
        LocalDateTime.of(2023,03, 1,10,00),
        500,40);
    flightsRepository.save(first);
    Flight second = new Flight("2F","Kharkiv","Kropyvnytskyi",
        LocalDateTime.of(2023,03, 2,15,30),
        700,50);
    flightsRepository.save(second);
    Flight third = new Flight("3C","Kharkiv","Lviv",
        LocalDateTime.of(2023,03, 3,22,15),
        900,45);
    flightsRepository.save(third);
  }
}