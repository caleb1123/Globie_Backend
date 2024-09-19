package com.product.globie.payload.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateAccountRequest {
    private String fullName;
    private String avatar;
    private Date dob;
    private String phone;
    private String email;
    private String sex;
    private String address;
}
