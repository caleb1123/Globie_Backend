package com.product.globie.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRateRequest {
    private int id; // ID of the rate
    private Integer rating; // Rating value (1-5, for example)
    private String description; // Description of the rating
    private int productId; // Product ID associated with the rate
}
