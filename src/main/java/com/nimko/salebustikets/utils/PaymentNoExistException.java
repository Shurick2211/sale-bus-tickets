package com.nimko.salebustikets.utils;

public class PaymentNoExistException extends Exception{
  static final String MESSAGE = "Payment not exist!";
  @Override
  public String getMessage() {
    return MESSAGE;
  }
}
