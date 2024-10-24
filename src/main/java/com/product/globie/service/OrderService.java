package com.product.globie.service;

import com.product.globie.payload.DTO.OrderDTO;
import com.product.globie.payload.DTO.OrderStoreDTO;
import com.product.globie.payload.request.CreateOrderRequest;
import com.product.globie.payload.request.CreateOrderStoreRequest;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(CreateOrderRequest createOrderRequest);
    List<OrderDTO> getAllOrderOfUser();

    List<OrderDTO> getOrderStatusPendingOfUser();

    List<OrderDTO> getOrderStatusShippingOfUser();

    List<OrderDTO> getOrders();

    List<OrderDTO> getOrderStatusCancel();

    List<OrderDTO> getOrderStatusShipping();

    List<OrderDTO> getOrderStatusPending();

    List<OrderDTO> getOrderStatusDeliveredOfUser();

    OrderStoreDTO createOrderStore(CreateOrderStoreRequest createOrderRequest);

    Integer countOrderByDay();

    Integer countOrderByYear();

    Integer countOrderShipping();

    Integer countOrderPending();

    Integer countOrderCanceled();

}
