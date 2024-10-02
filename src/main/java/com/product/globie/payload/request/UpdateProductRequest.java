package com.product.globie.payload.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateProductRequest {
    String productName;
    String description;
    String brand;
    String origin;
    double price;
    int quantity;
    String warranty;
}
