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
public class BookMarkDTO {
     int bookmarkId;

     Date createdTime;

     int userId;

     ProductDTO product;
}
