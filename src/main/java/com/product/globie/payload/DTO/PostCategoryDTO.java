package com.product.globie.payload.DTO;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostCategoryDTO {
     int postCategoryId;
     String categoryName;
     boolean status;
}
