package com.nimko.salebustikets.utils;

public class OnTheFlightNoSeatsException extends Exception{
  static final String MESSAGE = "On the flight No seats!";
  @Override
  public String getMessage() {
    return MESSAGE;
  }
}
