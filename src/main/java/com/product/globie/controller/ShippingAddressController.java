package com.product.globie.controller;

import com.product.globie.payload.DTO.CommentDTO;
import com.product.globie.payload.DTO.ShippingAddressDTO;
import com.product.globie.payload.request.CommentRequest;
import com.product.globie.payload.request.ShippingAddressRequest;
import com.product.globie.payload.request.UpdateCommentRequest;
import com.product.globie.payload.response.ApiResponse;
import com.product.globie.service.ShippingAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.version}/shippingAddress")
@Slf4j
@CrossOrigin(origins = "https://globie-front-cgxbtuyd8-dolakiens-projects.vercel.app")

public class ShippingAddressController {
    @Autowired
    ShippingAddressService shippingAddressService;

    @GetMapping("/all_of_user")
    public ResponseEntity<ApiResponse<List<ShippingAddressDTO>>> getAllShippingAddressOfUser() {
        List<ShippingAddressDTO> shippingAddressDTOS = shippingAddressService.getAllShippingAddressOfUser();
        ApiResponse<List<ShippingAddressDTO>> response = ApiResponse.<List<ShippingAddressDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Shipping Address")
                .data(shippingAddressDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ShippingAddressDTO>> addShippingAddress(@RequestBody ShippingAddressRequest shippingAddressRequest) {
        ShippingAddressDTO shippingAddressDTO = shippingAddressService.createShippingAddress(shippingAddressRequest);
        ApiResponse<ShippingAddressDTO> response = ApiResponse.<ShippingAddressDTO>builder()
                .code(HttpStatus.CREATED.value())
                .message("Successfully created Shipping Address")
                .data(shippingAddressDTO)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<ShippingAddressDTO>> updateShippingAddress(@RequestBody ShippingAddressRequest shippingAddressRequest, @PathVariable int id) {
        ShippingAddressDTO shippingAddressDTO = shippingAddressService.updateShippingAddress(shippingAddressRequest, id);
        ApiResponse<ShippingAddressDTO> response = ApiResponse.<ShippingAddressDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully updated Shipping Address")
                .data(shippingAddressDTO)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteShippingAddress(@PathVariable int id) {
        shippingAddressService.deleteShippingAddress(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully deleted Shipping Address")
                .build();
        return ResponseEntity.ok(response);
    }
}
