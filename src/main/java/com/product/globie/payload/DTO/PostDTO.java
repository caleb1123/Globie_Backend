package com.product.globie.payload.DTO;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDTO {
        int postId;

        String postTitle;

        String postContent;

        Date createdTime;

        Date updatedTime;

        boolean status;

        int postCategoryId;

        int userId;
}
