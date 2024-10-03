package com.product.globie.payload.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductImageDTO {
     int productImageId;

     String productImageCode;

     String imagePath;

     boolean status;

     int productId;
}
