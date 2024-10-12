package com.product.globie.service.impl;

import com.product.globie.config.Util;
import com.product.globie.entity.*;
import com.product.globie.entity.Enum.EOrderStatus;
import com.product.globie.entity.Enum.EPaymentMethod;
import com.product.globie.entity.Enum.EProductStatus;
import com.product.globie.payload.DTO.*;
import com.product.globie.payload.request.CreateOrderRequest;
import com.product.globie.payload.request.CreateOrderStoreRequest;
import com.product.globie.repository.*;
import com.product.globie.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    UserMemberRepository userMemberRepository;

    @Override
    public OrderDTO createOrder(CreateOrderRequest createOrderRequest) {
        Order order = new Order();
        order.setOrderCode(OrderCodeAutomationCreating());
        order.setOrderName("Product_Order");
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

    @Override
    public List<OrderDTO> getAllOrderOfUser() {
        int uId = util.getUserFromAuthentication().getUserId();
        List<Order> orders = orderRepository.findOrderByUser(uId);
        if(orders.isEmpty()){
            throw new RuntimeException("There are no Orders of this User: " + uId);
        }
        List<OrderDTO> orderDTOS = orders.stream()
                .map(order -> {
                    OrderDTO orderDTO = mapper.map(order, OrderDTO.class);

                    if (order.getShippingAddress() != null) {
                        orderDTO.setShippingId(order.getShippingAddress().getShippingId());
                    }
                    if (order.getUser() != null) {
                        orderDTO.setUserId(order.getUser().getUserId());
                    }

                    return orderDTO;
                })
                .collect(Collectors.toList());

        return orderDTOS.isEmpty() ? null : orderDTOS;    }

    @Override
    public List<OrderDTO> getOrderStatusPendingOfUser() {
        int uId = util.getUserFromAuthentication().getUserId();
        List<Order> orders = orderRepository.findOrderByUser(uId);
        if(orders.isEmpty()){
            throw new RuntimeException("There are no Orders of this User: " + uId);
        }
        List<OrderDTO> orderDTOS = orders.stream()
                .filter(Order -> Order.getStatus().equals(EOrderStatus.PENDING.name()))
                .map(order -> {
                    OrderDTO orderDTO = mapper.map(order, OrderDTO.class);

                    if (order.getShippingAddress() != null) {
                        orderDTO.setShippingId(order.getShippingAddress().getShippingId());
                    }
                    if (order.getUser() != null) {
                        orderDTO.setUserId(order.getUser().getUserId());
                    }

                    return orderDTO;
                })
                .collect(Collectors.toList());

        return orderDTOS.isEmpty() ? null : orderDTOS;
    }

    @Override
    public List<OrderDTO> getOrderStatusShippingOfUser() {
        int uId = util.getUserFromAuthentication().getUserId();
        List<Order> orders = orderRepository.findOrderByUser(uId);
        if(orders.isEmpty()){
            throw new RuntimeException("There are no Orders of this User: " + uId);
        }
        List<OrderDTO> orderDTOS = orders.stream()
                .filter(Order -> Order.getStatus().equals(EOrderStatus.SHIPPING.name()))
                .map(order -> {
                    OrderDTO orderDTO = mapper.map(order, OrderDTO.class);

                    if (order.getShippingAddress() != null) {
                        orderDTO.setShippingId(order.getShippingAddress().getShippingId());
                    }
                    if (order.getUser() != null) {
                        orderDTO.setUserId(order.getUser().getUserId());
                    }

                    return orderDTO;
                })
                .collect(Collectors.toList());

        return orderDTOS.isEmpty() ? null : orderDTOS;    }

    @Override
    public List<OrderDTO> getOrderStatusDeliveredOfUser() {
        int uId = util.getUserFromAuthentication().getUserId();
        List<Order> orders = orderRepository.findOrderByUser(uId);
        if(orders.isEmpty()){
            throw new RuntimeException("There are no Orders of this User: " + uId);
        }
        List<OrderDTO> orderDTOS = orders.stream()
                .filter(Order -> Order.getStatus().equals(EOrderStatus.DELIVERED.name()))
                .map(order -> {
                    OrderDTO orderDTO = mapper.map(order, OrderDTO.class);

                    if (order.getShippingAddress() != null) {
                        orderDTO.setShippingId(order.getShippingAddress().getShippingId());
                    }
                    if (order.getUser() != null) {
                        orderDTO.setUserId(order.getUser().getUserId());
                    }

                    return orderDTO;
                })
                .collect(Collectors.toList());

        return orderDTOS.isEmpty() ? null : orderDTOS;
    }

    @Override
    public OrderStoreDTO createOrderStore(CreateOrderStoreRequest createOrderRequest) {
        Order order = new Order();
        order.setOrderCode(OrderCodeAutomationCreating());
        order.setOrderName("Store_Order");
        order.setOrderDate(new Date());
        order.setPaymentMethodOrder(EPaymentMethod.ELECTRONIC_PAYMENT.name());
        order.setStatus(EOrderStatus.PENDING.name());
        order.setUser(util.getUserFromAuthentication());
        int shippingID = 1;
            ShippingAddress shippingAddress = shippingAddressRepository.findById(shippingID)
                    .orElse(null); // Trả về null nếu không tìm thấy
            order.setShippingAddress(shippingAddress); // Nếu null thì shipping_id trong DB sẽ là NULL

        MemberLevel memberLevel = memberRepository.findById((createOrderRequest.getMemberID()))
                .orElseThrow(() -> new RuntimeException("Member Level not found with ID: " + createOrderRequest.getMemberID()));

        // Kiểm tra số điện thoại, tên cửa hàng và địa chỉ
        if (userMemberRepository.findByStorePhone(createOrderRequest.getStorePhone()).isPresent()) {
            throw new RuntimeException("Store phone already exists!");
        }

        if (userMemberRepository.findByStoreName(createOrderRequest.getStoreName()).isPresent()) {
            throw new RuntimeException("Store name already exists!");
        }

        if (userMemberRepository.findByStoreAddress(createOrderRequest.getStoreAddress()).isPresent()) {
            throw new RuntimeException("Store address already exists!");
        }

        order.setTotalAmount(memberLevel.getPrice());
        Order savesOrder = orderRepository.save(order);

        UserMember userMember = new UserMember();
        userMember.setStoreName(createOrderRequest.getStoreName());
        userMember.setDescription(createOrderRequest.getDescription());
        userMember.setStoreAddress(createOrderRequest.getStoreAddress());
        userMember.setStorePhone(createOrderRequest.getStorePhone());
        userMember.setMemberStartDate(LocalDate.now());
        userMember.setMemberEndDate(userMember.getMemberStartDate().plusMonths(memberLevel.getDurationInMonths()));
        userMember.setMemberLevel(memberLevel);
        userMember.setStatus(false);
        UserMember userMember1 = userMemberRepository.findByUserIdAndStatusTrue(util.getUserFromAuthentication().getUserId());
        if(userMember1 == null){
            userMember.setUser(util.getUserFromAuthentication());
        }else {
            throw new RuntimeException("You have purchased the package: " + userMember1.getMemberLevel().getLevelName());
        }
        userMember.setOrder(order);
        userMemberRepository.save(userMember);

        OrderStoreDTO orderStoreDTO = mapper.map(savesOrder, OrderStoreDTO.class);
        orderStoreDTO.setUserId(order.getUser().getUserId());
        orderStoreDTO.setUserMemberId(userMember.getUserMemberId());

        return orderStoreDTO;
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
