package com.product.globie.payload.DTO;

import com.product.globie.entity.Post;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCategoryDTO {
    private int productCategoryId;

    private String categoryName;

    private String description;

    private boolean status;
}
