package com.product.globie.payload.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
     String productName;
     String description;
     String brand;
     String origin;
     double price;
     int quantity;
     int productCategoryId;
}
