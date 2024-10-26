package com.product.globie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.product.globie.config.PayOSConfig;
import com.product.globie.entity.Enum.EPaymentMethod;
import com.product.globie.entity.Order;
import com.product.globie.payload.DTO.OrderDTO;
import com.product.globie.payload.DTO.OrderStoreDTO;
import com.product.globie.payload.request.CreateOrderRequest;
import com.product.globie.payload.request.CreateOrderStoreRequest;
import com.product.globie.payload.request.PaymentRequest;
import com.product.globie.payload.response.PayOSResponse;
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
import java.util.Map;

@RestController
@RequestMapping("${api.version}/payment")
@Slf4j
@CrossOrigin("https://globie-front-9hx0i0h1i-dolakiens-projects.vercel.app")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @Autowired
    OrderService orderService;

    @Autowired
    PayOSConfig payOSConfig;

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
        String url = "http://localhost:3000/vnpay-return";
        String urlFail = "http://localhost:3000";

        PaymentResponse payment = paymentService.handleCallback(request);
        if (payment.getCode().equals("00")) {
            response.sendRedirect(url);
        } else {
            response.sendRedirect(urlFail);
        }
    }

    @PostMapping("/pay-os-create")
    public ResponseObject<PayOSResponse>PayOS(@RequestBody CreateOrderRequest request, HttpServletRequest httpServletRequest) throws Exception {
        OrderDTO orderDTO = orderService.createOrder(request);
        if(orderDTO.getPaymentMethodOrder().equals(EPaymentMethod.ELECTRONIC_PAYMENT.name())) {
            return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createPaymentLink(orderDTO.getOrderId(), httpServletRequest));
        }
        else{
            return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.changeOrderStatusPayOs(orderDTO.getOrderId()));
        }
    }

    @PostMapping("/payos-store")
    public ResponseObject<PayOSResponse>PayOSStore(@RequestBody CreateOrderStoreRequest request, HttpServletRequest httpServletRequest) throws Exception {
        OrderStoreDTO orderStoreDTO = orderService.createOrderStore(request);
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createPaymentLink(orderStoreDTO.getOrderId(), httpServletRequest));
    }

    @GetMapping("/payos_call_back")
    public void payOSCallbackHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = "https://globie-front-9hx0i0h1i-dolakiens-projects.vercel.app/payment-return";
        String urlFail = "https://globie-front-9hx0i0h1i-dolakiens-projects.vercel.app/payment-return";

        PaymentResponse payment = paymentService.handleCallbackPayOS(request);
        if (payment.getCode().equals("00")) {
            response.sendRedirect(url);
        } else {
            response.sendRedirect(urlFail);
        }
    }
}

