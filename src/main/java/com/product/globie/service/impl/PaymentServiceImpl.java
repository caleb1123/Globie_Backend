package com.product.globie.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.PaymentData;
import vn.payos.type.ItemData;
import com.product.globie.VNPay.VNPayUtil;
import com.product.globie.config.PayOSConfig;
import com.product.globie.config.Util;
import com.product.globie.config.VnPayConfig;
import com.product.globie.entity.*;
import com.product.globie.entity.Enum.EOrderStatus;
import com.product.globie.entity.Enum.EPaymentStatus;
import com.product.globie.entity.Enum.ERole;
import com.product.globie.payload.request.PaymentRequest;
import com.product.globie.payload.response.PayOSResponse;
import com.product.globie.payload.response.PaymentResponse;
import com.product.globie.payload.response.VNPayResponse;
import com.product.globie.repository.*;
import com.product.globie.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    VnPayConfig vnPayConfig;

    @Autowired
    PayOSConfig payOSConfig;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    Util util;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    UserMemberRepository userMemberRepository;

    @Autowired
    RoleRepository roleRepository;


    @Override
    public VNPayResponse vnPayPayment(int orderId, HttpServletRequest request) {
        // Lấy đơn hàng dựa trên order_code
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Not found this ORDER: " + orderId));

        // Lấy người dùng dựa trên user_id của đơn hàng
        User user = userRepository.findById(order.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("USER not found with Id: " + order.getUser().getUserId()));

        // Kiểm tra nếu đã có giao dịch thành công
        List<Payment> paymentHistory = paymentRepository.findByOrderId(order.getOrderId());
        if (!paymentHistory.isEmpty() && paymentHistory.stream().anyMatch(payment -> payment.getStatus().equals("SUCCESSFUL"))) {
            throw new RuntimeException("Payment is already completed");
        }

        // Tính số tiền cần thanh toán
        Long amount = (long) (order.getTotalAmount() * 100L);
        String bankCode = "NCB";
        Map<String, String> vnpParamsMap;

        vnpParamsMap = vnPayConfig.getVNPayConfig(order.getOrderId(), user.getUserName(), user.getUserId(), order.getOrderName());
        // Thiết lập các tham số cho VNPay
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));

        // Xây dựng URL yêu cầu thanh toán
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;

        // Tạo và lưu thông tin thanh toán mới
        Payment payment = new Payment();
        payment.setPaymentAmount(order.getTotalAmount());
        payment.setStatus(EPaymentStatus.PENDING.name());
        payment.setPaymentDate(new Date());
        payment.setOrder(order);
        payment.setPaymentCode(vnpParamsMap.get("vnp_TxnRef"));
        payment.setPaymentMethod("Thanh toán ngân hàng");
        paymentRepository.save(payment);

        // Trả về kết quả
        return VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl)
                .build();
    }

    @Override
    public PaymentResponse handleCallback(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        String paymentCode = request.getParameter("vnp_TxnRef");
        String type = request.getParameter("vnp_CardType");
        String orderId = request.getParameter("orderId");
        String amount = request.getParameter("vnp_Amount");
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String payDate = request.getParameter("vnp_PayDate");
        String transactionNo = request.getParameter("vnp_TransactionNo");
        String userName = request.getParameter("username");


        Payment payment = paymentRepository.findPaymentByPaymentCode(paymentCode);
        if (status.equals("00")) {
            payment.setStatus(EPaymentStatus.SUCCESSFULLY.name());
            payment.setPaymentDate(new Date());
            payment.setPaymentMethod(type);
            paymentRepository.save(payment);

            Order order = orderRepository.findById(Integer.valueOf(orderId))
                    .orElseThrow(() -> new RuntimeException("Order not found!"));
            if(order.getOrderName().equals("Product_Order")){ order.setStatus(EOrderStatus.SHIPPING.name());}
            else {
                order.setStatus(EOrderStatus.SUCCESSFUL.name());
                UserMember userMember = userMemberRepository.findByOrder(order.getOrderId())
                        .orElseThrow(() -> new RuntimeException("User Member not found!"));
                userMember.setStatus(true);
                userMemberRepository.save(userMember);

                User user = userRepository.findById(util.getUserFromAuthentication().getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found with id: " + userMember.getUser().getUserId()));
                Role role = roleRepository.findById(2)
                        .orElseThrow(() -> new RuntimeException("Role not found!"));
                user.setRole(role);
                userRepository.save(user);
            }

            orderRepository.save(order);

            Transaction transaction = new Transaction();
            transaction.setAmount(Double.valueOf(amount));
            transaction.setOrder(order);
            transaction.setTransactionCode(transactionNo);
            Date transactionDate = setTransactionDateFromPayDate(payDate);
            transaction.setTransactionDate(transactionDate);
            transaction.setDescription(orderInfo);
            User user = userRepository.findByUserName(userName)
                    .orElseThrow(() -> new RuntimeException("User Not found!"));
            transaction.setUser(user);
            transactionRepository.save(transaction);

            return new PaymentResponse(status, "SUCCESSFUL", mapper.map(payment, PaymentResponse.class));
        }
        else {
            payment.setStatus(EPaymentStatus.FAILURE.name());
            paymentRepository.save(payment);
            Order order = orderRepository.findById(Integer.valueOf(orderId))
                    .orElseThrow(() -> new RuntimeException("Order not found!"));
            order.setStatus(EOrderStatus.FAILED.name());
            orderRepository.save(order);
            return new PaymentResponse(status, "FAILED", mapper.map(payment, PaymentResponse.class));
        }
    }

    @Override
    public VNPayResponse changeOrderStatus(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found!"));
            order.setStatus(EOrderStatus.SHIPPING.name());
            order.setOrderDate(new Date());
            orderRepository.save(order);
        return VNPayResponse.builder()
                .code("ok")
                .message("Order Successfully!")
                .build();
    }

    @Override
    public PayOSResponse changeOrderStatusPayOs(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found!"));
        order.setStatus(EOrderStatus.SHIPPING.name());
        order.setOrderDate(new Date());
        orderRepository.save(order);
        return PayOSResponse.builder()
                .error("ok")
                .message("Order Successfully!")
                .build();
    }

    @Override
    public PayOSResponse createPaymentLink(int orderId, HttpServletRequest request) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        // Lấy đơn hàng dựa trên order_code
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Not found this ORDER: " + orderId));

        // Lấy người dùng dựa trên user_id của đơn hàng
        User user = userRepository.findById(order.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("USER not found with Id: " + order.getUser().getUserId()));

        // Kiểm tra nếu đã có giao dịch thành công
        List<Payment> paymentHistory = paymentRepository.findByOrderId(order.getOrderId());
        if (!paymentHistory.isEmpty() && paymentHistory.stream().anyMatch(payment -> payment.getStatus().equals("SUCCESSFUL"))) {
            throw new RuntimeException("Payment is already completed");
        }

        int totalAmount = (int) Math.round(order.getTotalAmount());
        long orderCode = Long.parseLong(order.getOrderCode());
        String baseUrl = getBaseUrl(request);

        ItemData itemData = ItemData.builder()
                .name(order.getOrderName())
                .quantity(1)
                .price(totalAmount)
                .build();

        // Xây dựng returnUrl và cancelUrl
        String returnUrl = baseUrl + "/payment-return" +
                "?orderId=" + order.getOrderId() +
                "&userName=" + user.getUserName() +
                "&amount=" + itemData.getPrice() +
                "&name=" + itemData.getName();

        String cancelUrl = baseUrl + "/payment-return"  +
                "?orderId=" + order.getOrderId() +
                "&userName=" + user.getUserName() +
                "&amount=" + itemData.getPrice() +
                "&name=" + itemData.getName();

        PaymentData paymentData = PaymentData.builder()
                .orderCode(orderCode)
                .amount(totalAmount)
                .description("Thanh toán đơn hàng")
                .returnUrl(returnUrl)  // Sử dụng returnUrl đã xây dựng
                .cancelUrl(cancelUrl)  // Sử dụng cancelUrl đã xây dựng
                .item(itemData)
                .build();

        CheckoutResponseData result = payOSConfig.payOS().createPaymentLink(paymentData);

        Payment payment = new Payment();
        payment.setPaymentAmount(order.getTotalAmount());
        payment.setStatus(EPaymentStatus.PENDING.name());
        payment.setPaymentDate(new Date());
        payment.setOrder(order);
        payment.setPaymentCode(order.getOrderCode());
        payment.setPaymentMethod("Thanh toán ngân hàng");
        paymentRepository.save(payment);

        // Trả về kết quả
        return PayOSResponse.builder()
                .error("ok")
                .message("success")
                .data(objectMapper.valueToTree(result))
                .build();
    }


    @Override
    public PaymentResponse handleCallbackPayOS(HttpServletRequest request) {
        String status = request.getParameter("status");
        String code = request.getParameter("code");
        String orderId = request.getParameter("orderId");
        String transactionNo = request.getParameter("id");
        String userName = request.getParameter("userName");
        String orderCode = request.getParameter("orderCode");
        String amount = request.getParameter("amount");

        Payment payment = paymentRepository.findPaymentByPaymentCode(orderCode);

            if (code.equals("00") && status.equals("PAID")) {
            payment.setStatus(EPaymentStatus.SUCCESSFULLY.name());
            payment.setPaymentDate(new Date());
            paymentRepository.save(payment);

            Order order = orderRepository.findById(Integer.valueOf(orderId))
                    .orElseThrow(() -> new RuntimeException("Order not found!"));
            if(order.getOrderName().equals("Product_Order")){ order.setStatus(EOrderStatus.SHIPPING.name());}
            else {
                order.setStatus(EOrderStatus.SUCCESSFUL.name());
                UserMember userMember = userMemberRepository.findByOrder(order.getOrderId())
                        .orElseThrow(() -> new RuntimeException("User Member not found!"));
                userMember.setStatus(true);
                userMemberRepository.save(userMember);

                User user = userRepository.findById(util.getUserFromAuthentication().getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found with id: " + userMember.getUser().getUserId()));
                Role role = roleRepository.findById(2)
                        .orElseThrow(() -> new RuntimeException("Role not found!"));
                user.setRole(role);
                userRepository.save(user);
            }

            orderRepository.save(order);

            Transaction transaction = new Transaction();
            transaction.setAmount(Double.valueOf(amount));
            transaction.setOrder(order);
            transaction.setTransactionCode(transactionNo);
            transaction.setTransactionDate(new Date());
            transaction.setDescription("Thanh toan don hang: " + orderCode);
            User user = userRepository.findByUserName(userName)
                    .orElseThrow(() -> new RuntimeException("User Not found!"));
            transaction.setUser(user);
            transactionRepository.save(transaction);

            return new PaymentResponse(status, "SUCCESSFUL", mapper.map(payment, PaymentResponse.class));
        }
        else {
                payment.setStatus(EPaymentStatus.FAILURE.name());
                paymentRepository.save(payment);

                Order order = orderRepository.findById(Integer.valueOf(orderId))
                        .orElseThrow(() -> new RuntimeException("Order not found!"));
                order.setStatus(EOrderStatus.CANCELLED.name());
                orderRepository.save(order);

                return new PaymentResponse(status, "CANCELLED", mapper.map(payment, PaymentResponse.class));
        }
    }

    private String getBaseUrl(HttpServletRequest request) {
        // Trả về URL cố định
        return "https://globie.vercel.app";
    }



    private Date setTransactionDateFromPayDate(String payDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

        try {
            return formatter.parse(payDate);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid date format for payDate: " + payDate);
        }
    }
}
