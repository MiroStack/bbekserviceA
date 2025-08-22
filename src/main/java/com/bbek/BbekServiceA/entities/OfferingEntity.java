package com.bbek.BbekServiceA.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "offerings")
@AllArgsConstructor
@NoArgsConstructor
public class OfferingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_name")
    private String memberName;

    @Column(name="amount")
    double amount;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "offering_date")
    private LocalDateTime offeringDate;

    @Column(name="offering_type")
    private Long offeringType;

    @Column(name="payment_method")
    private Long paymentMethod;

    @Column(name="notes")
    private String notes;

}
