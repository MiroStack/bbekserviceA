package com.bbek.BbekServiceA.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor

public class OfferingModel {
    private Long id;
    private String memberName;
    double amount;
    private LocalDateTime offeringDate;
    private String offeringType;
    private String paymentMethod;
    private String notes;
}
