package com.product.globie.payload.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateMemberRequest {
    String levelName;

    String description;

    Double price;

    Integer durationInMonths;
}
