package com.product.globie.payload.response;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    private int productId;
    private String productName;
    private String description;
    private String brand;
    private String origin;
    private double price;
    private int quantity;
    private Date createdTime;
    private Date updatedTime;
    private boolean status;
    private int productCategoryId;
    private int userId;
}
