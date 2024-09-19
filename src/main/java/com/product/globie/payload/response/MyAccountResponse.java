package com.product.globie.payload.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyAccountResponse {
    private String userName;
    private String fullName;
    private String avatar;
    private Date dob;
    private String phone;
    private String email;
    private String sex;
    private String address;
}
