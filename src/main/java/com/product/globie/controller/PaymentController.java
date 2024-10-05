package com.product.globie.controller;

import com.product.globie.entity.Enum.EPaymentMethod;
import com.product.globie.entity.Order;
import com.product.globie.payload.DTO.OrderDTO;
import com.product.globie.payload.request.CreateOrderRequest;
import com.product.globie.payload.request.PaymentRequest;
import com.product.globie.payload.response.PaymentResponse;
import com.product.globie.payload.response.ResponseObject;
import com.product.globie.payload.response.VNPayResponse;
import com.product.globie.service.OrderService;
import com.product.globie.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("${api.version}/payment")
@Slf4j
@CrossOrigin("http://localhost:3000")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @Autowired
    OrderService orderService;

    @PostMapping("/vn-pay")
    public ResponseObject<VNPayResponse>VNPay(@RequestBody CreateOrderRequest request, HttpServletRequest httpServletRequest) {
        OrderDTO orderDTO = orderService.createOrder(request);
        if(orderDTO.getPaymentMethodOrder().equals(EPaymentMethod.ELECTRONIC_PAYMENT.name())) {
            return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.vnPayPayment(orderDTO.getOrderId(), httpServletRequest));
        }else{
            return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.changeOrderStatus(orderDTO.getOrderId()));
        }
    }


    @GetMapping("/call-back")
    public void payCallbackHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = "http://localhost:3000";
        String urlFail = "http://localhost:3000";
        PaymentResponse payment = paymentService.handleCallback(request);
        if (payment.getCode().equals("00")) {
            response.sendRedirect(url);
        } else {
            response.sendRedirect(urlFail);
        }
    }
}

