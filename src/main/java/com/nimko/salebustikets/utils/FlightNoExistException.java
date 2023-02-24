package com.nimko.salebustikets.utils;

public class FlightNoExistException extends Exception{
  static final String MESSAGE = "Flight Not exist!";
  @Override
  public String getMessage() {
    return MESSAGE;
  }
}
