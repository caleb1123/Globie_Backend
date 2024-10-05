package com.product.globie.payload.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VNPayResponse {
    public String code;
    public String message;
    public String paymentUrl;
}
