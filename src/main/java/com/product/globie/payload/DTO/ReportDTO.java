package com.product.globie.payload.DTO;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportDTO {
     int reportId;

     String message;

     String status;

     String createdTime;

     String updatedTime;

     int productId;

     int userId;
}
