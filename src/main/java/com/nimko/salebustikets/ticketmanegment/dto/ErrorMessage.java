package com.nimko.salebustikets.ticketmanegment.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorMessage {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;

}
