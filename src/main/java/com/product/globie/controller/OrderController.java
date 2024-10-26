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
@CrossOrigin(origins = "https://globie-front-cgxbtuyd8-dolakiens-projects.vercel.app")

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

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrders() {
        List<OrderDTO> orderDTOS = orderService.getOrders();
        ApiResponse<List<OrderDTO>> response = ApiResponse.<List<OrderDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Order")
                .data(orderDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all_shipping")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrdersShipping() {
        List<OrderDTO> orderDTOS = orderService.getOrderStatusShipping();
        ApiResponse<List<OrderDTO>> response = ApiResponse.<List<OrderDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Order")
                .data(orderDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all_pending")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrdersPending() {
        List<OrderDTO> orderDTOS = orderService.getOrderStatusPending();
        ApiResponse<List<OrderDTO>> response = ApiResponse.<List<OrderDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Order")
                .data(orderDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all_cancel")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrdersCancel() {
        List<OrderDTO> orderDTOS = orderService.getOrderStatusCancel();
        ApiResponse<List<OrderDTO>> response = ApiResponse.<List<OrderDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Order")
                .data(orderDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count_day")
    public ResponseEntity<ApiResponse<Integer>> countOrderByDay() {
        int countDay = orderService.countOrderByDay();
        ApiResponse<Integer> response = ApiResponse.<Integer>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Order")
                .data(countDay)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count_year")
    public ResponseEntity<ApiResponse<Integer>> countOrderByYear() {
        int countYear = orderService.countOrderByYear();
        ApiResponse<Integer> response = ApiResponse.<Integer>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Order")
                .data(countYear)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count_shipping")
    public ResponseEntity<ApiResponse<Integer>> countOrderShipping() {
        int count = orderService.countOrderShipping();
        ApiResponse<Integer> response = ApiResponse.<Integer>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Order")
                .data(count)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count_pending")
    public ResponseEntity<ApiResponse<Integer>> countOrderPending() {
        int count = orderService.countOrderPending();
        ApiResponse<Integer> response = ApiResponse.<Integer>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Order")
                .data(count)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count_cancel")
    public ResponseEntity<ApiResponse<Integer>> countOrderCancel() {
        int count = orderService.countOrderCanceled();
        ApiResponse<Integer> response = ApiResponse.<Integer>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Order")
                .data(count)
                .build();
        return ResponseEntity.ok(response);
    }
}
