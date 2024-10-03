package com.product.globie.payload.DTO;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostImageDTO {
    private int postImageId;

    private String imagePath;

    private boolean status;

    private String postImageCode;

    private int postId;
}
