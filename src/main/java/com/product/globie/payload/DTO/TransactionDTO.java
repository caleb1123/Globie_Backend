package com.product.globie.payload.DTO;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionDTO {
    private int transactionId;

    private String transactionCode;

    private Date transactionDate;

    private String description;

    private Double amount;

    private int userID;

    private int orderCode;
}
