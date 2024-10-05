package com.product.globie.service.impl;

import com.product.globie.VNPay.VNPayUtil;
import com.product.globie.config.Util;
import com.product.globie.config.VnPayConfig;
import com.product.globie.entity.Enum.EOrderStatus;
import com.product.globie.entity.Enum.EPaymentStatus;
import com.product.globie.entity.Order;
import com.product.globie.entity.Payment;
import com.product.globie.entity.Transaction;
import com.product.globie.entity.User;
import com.product.globie.payload.request.PaymentRequest;
import com.product.globie.payload.response.PaymentResponse;
import com.product.globie.payload.response.VNPayResponse;
import com.product.globie.repository.*;
import com.product.globie.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        vnpParamsMap = vnPayConfig.getVNPayConfig(order.getOrderId(), user.getUserName(), user.getUserId());
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

        Payment payment = paymentRepository.findPaymentByPaymentCode(paymentCode);
        if (status.equals("00")) {
            payment.setStatus(EPaymentStatus.SUCCESSFULLY.name());
            payment.setPaymentDate(new Date());
            payment.setPaymentMethod(type);
            paymentRepository.save(payment);

            Order order = orderRepository.findById(Integer.valueOf(orderId))
                    .orElseThrow(() -> new RuntimeException("Order not found!"));
            order.setStatus(EOrderStatus.SHIPPED.name());
            orderRepository.save(order);

            Transaction transaction = new Transaction();
            transaction.setAmount(Double.valueOf(amount));
            transaction.setOrder(order);
            transaction.setTransactionCode(transactionNo);
            Date transactionDate = setTransactionDateFromPayDate(payDate);
            transaction.setTransactionDate(transactionDate);
            transaction.setDescription(orderInfo);
            transaction.setUser(util.getUserFromAuthentication());
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
            order.setStatus(EOrderStatus.SHIPPED.name());
            order.setOrderDate(new Date());
            orderRepository.save(order);
        return VNPayResponse.builder()
                .code("ok")
                .message("Order Successfully!")
                .build();
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
