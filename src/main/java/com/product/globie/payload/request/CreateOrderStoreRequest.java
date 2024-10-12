package com.product.globie.payload.request;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderStoreRequest {

    private String storeName;

    private String description;

    private String storeAddress;

    private String storePhone;

    private int memberID;
}
