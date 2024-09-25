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
public class CommentDTO {
     int id;

     String content;

     Date createdTime;

     Date updatedTime;

     int userId;

     int postId;
}
