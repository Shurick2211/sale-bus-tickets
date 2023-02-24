package com.nimko.salebustikets.payment.models;

import com.nimko.salebustikets.utils.PaymentStatus;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String fullName;
    private double sum;

    private PaymentStatus status;

    public Payment(String fullName, double sum) {
        this.fullName = fullName;
        this.sum = sum;
        this.status = PaymentStatus.NEW;
    }
}
