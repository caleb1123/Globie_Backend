package com.product.globie.payload.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderStoreDTO {
    int orderId;

    String orderCode;

    Date orderDate;

    Double totalAmount;

    String status;

    int userMemberId;

    int userId;
}
