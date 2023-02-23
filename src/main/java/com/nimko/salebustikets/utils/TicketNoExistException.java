package com.nimko.salebustikets.utils;

public class TicketNoExistException extends Exception{
  static final String MESSAGE = "Ticket No exist!";
  @Override
  public String getMessage() {
    return MESSAGE;
  }
}
