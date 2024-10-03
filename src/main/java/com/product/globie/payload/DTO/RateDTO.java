package com.product.globie.payload.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RateDTO {
    private int id; // ID of the rate
    private Integer rating; // Rating value (1-5, for example)
    private String description; // Description of the rating
    private String createdTime; // Creation time of the rate
    private String updatedTime; // Last updated time of the rate
    private int userId; // User ID associated with the rate
    private int productId; // Product ID associated with the rate
}
