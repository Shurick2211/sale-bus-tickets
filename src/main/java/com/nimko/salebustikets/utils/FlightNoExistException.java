package com.nimko.salebustikets.utils;

public class FlightNoExistException extends Exception{
  static final String MESSAGE = "Flight No exist!";
  @Override
  public String getMessage() {
    return MESSAGE;
  }
}
