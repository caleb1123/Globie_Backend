package com.product.globie.service.impl;

import com.product.globie.config.Util;
import com.product.globie.entity.*;
import com.product.globie.entity.Enum.EOrderStatus;
import com.product.globie.entity.Enum.EProductStatus;
import com.product.globie.payload.DTO.OrderDTO;
import com.product.globie.payload.DTO.PostDTO;
import com.product.globie.payload.DTO.ProductOrderDTO;
import com.product.globie.payload.request.CreateOrderRequest;
import com.product.globie.repository.OrderDetailRepository;
import com.product.globie.repository.OrderRepository;
import com.product.globie.repository.ProductRepository;
import com.product.globie.repository.ShippingAddressRepository;
import com.product.globie.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    Util util;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ShippingAddressRepository shippingAddressRepository;

    @Override
    public OrderDTO createOrder(CreateOrderRequest createOrderRequest) {
        Order order = new Order();
        order.setOrderCode(OrderCodeAutomationCreating());
        order.setOrderDate(new Date());
        order.setPaymentMethodOrder(createOrderRequest.getPaymentMethodOrder());
        order.setStatus(EOrderStatus.PENDING.name());

        ShippingAddress shippingAddress = shippingAddressRepository.findById(createOrderRequest.getShippingId())
                .orElseThrow(() -> new RuntimeException("Shipping address not found!"));

        if(!shippingAddress.isStatus()){
            throw new RuntimeException("You need to select a default shipping address. ID: " + shippingAddress.getShippingId());
        }
        order.setShippingAddress(shippingAddress);
        order.setUser(util.getUserFromAuthentication());
        double totalAmount = 0;
        order.setTotalAmount(totalAmount);
        Order savesOrder = orderRepository.save(order);

        for (ProductOrderDTO productOrder : createOrderRequest.getProductOrders()) {
            Integer productId = productOrder.getProductId();
            Integer amount = productOrder.getAmount();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            if (product.getQuantity() >= amount) {
                orderDetail.setAmount(amount);
                int remainingProduct = product.getQuantity() - amount;
                product.setQuantity(remainingProduct);
                if(remainingProduct == 0){
                    product.setStatus(EProductStatus.Sold.name());
                    product.setUpdatedTime(new Date());
                }
                productRepository.save(product);
            } else {
                throw new RuntimeException("The number of products ordered is greater than the number of products in stock. ID: " + product.getProductId());
            }

            orderDetail.setProduct(product);
            orderDetailRepository.save(orderDetail);

            totalAmount += amount * product.getPrice();
        }
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);

        OrderDTO orderDTO = mapper.map(savesOrder, OrderDTO.class);
        orderDTO.setUserId(order.getUser().getUserId());
        orderDTO.setShippingId(savesOrder.getShippingAddress().getShippingId());

        return orderDTO;
    }

    private String OrderCodeAutomationCreating() {
        User user = util.getUserFromAuthentication();
        int userId = user.getUserId(); // Giả định bạn có hàm lấy userId

        // Lấy thời gian hiện tại
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());

        // Tạo phần số thứ tự tự động hoặc ngẫu nhiên cho mã đơn hàng
        // Bạn có thể thay thế bằng logic lấy số thứ tự từ DB
        String uniqueId = String.format("%04d", new Random().nextInt(10000));

        return "USER" + userId + "-" + date + "-" + uniqueId;
    }
}
