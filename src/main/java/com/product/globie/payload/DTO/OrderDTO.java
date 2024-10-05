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
public class OrderDTO {
     int orderId;

     String orderCode;

     Date orderDate;

     Double totalAmount;

     String status;

     String paymentMethodOrder;

     int userId;

     int shippingId;
}
