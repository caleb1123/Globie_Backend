package com.product.globie.controller;

import com.product.globie.payload.DTO.OrderDTO;
import com.product.globie.payload.DTO.ProductDTO;
import com.product.globie.payload.response.ApiResponse;
import com.product.globie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.version}/order")
@Slf4j
@CrossOrigin("http://localhost:3000")
public class OrderController {
    @Autowired
    OrderService orderService;


    @GetMapping("/all_of_user")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getAllOrderByUser() {
        List<OrderDTO> orderDTOS = orderService.getAllOrderOfUser();
        ApiResponse<List<OrderDTO>> response = ApiResponse.<List<OrderDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Order")
                .data(orderDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all_of_user_shipping")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrderByUserStatusShipping() {
        List<OrderDTO> orderDTOS = orderService.getOrderStatusShippingOfUser();
        ApiResponse<List<OrderDTO>> response = ApiResponse.<List<OrderDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Order")
                .data(orderDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all_of_user_delivered")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrderByUserStatusDelivered() {
        List<OrderDTO> orderDTOS = orderService.getOrderStatusDeliveredOfUser();
        ApiResponse<List<OrderDTO>> response = ApiResponse.<List<OrderDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Order")
                .data(orderDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all_of_user_pending")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrderByUserStatusPending() {
        List<OrderDTO> orderDTOS = orderService.getOrderStatusPendingOfUser();
        ApiResponse<List<OrderDTO>> response = ApiResponse.<List<OrderDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Order")
                .data(orderDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

}
