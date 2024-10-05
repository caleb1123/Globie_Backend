package com.product.globie.payload.request;

import com.product.globie.payload.DTO.ProductOrderDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderRequest {
    String orderCode;

    String paymentMethodOrder;

    int shippingId;

    private List<ProductOrderDTO> productOrders;
}
