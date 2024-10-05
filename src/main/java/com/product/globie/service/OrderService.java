package com.product.globie.service;

import com.product.globie.payload.DTO.OrderDTO;
import com.product.globie.payload.request.CreateOrderRequest;

public interface OrderService {
    OrderDTO createOrder(CreateOrderRequest createOrderRequest);
}
