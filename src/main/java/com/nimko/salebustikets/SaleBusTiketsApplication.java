package com.nimko.salebustikets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SaleBusTiketsApplication {

  public static void main(String[] args) {
    SpringApplication.run(SaleBusTiketsApplication.class, args);
  }

}
