package com.nimko.salebustikets.ticketmanegment.controllers;


import com.nimko.salebustikets.ticketmanegment.dto.ErrorMessage;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class TicketAdviceController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<?> error(Exception exception) {
        String mess = exception.getMessage();
        log.error(mess);
        ErrorMessage message = new ErrorMessage(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(),
            "Ticket service error", mess);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

}

