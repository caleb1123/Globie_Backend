package com.product.globie.controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.proc.BadJWTException;
import com.product.globie.exception.AppException;
import com.product.globie.payload.request.AuthenticationRequest;
import com.product.globie.payload.request.IntrospectRequest;
import com.product.globie.payload.request.LogoutRequest;
import com.product.globie.payload.response.ApiResponse;
import com.product.globie.payload.response.AuthenticationResponse;
import com.product.globie.payload.request.SignUpRequest;
import com.product.globie.payload.response.IntrospectResponse;
import com.product.globie.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("${api.version}/authen")
@Slf4j
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/register")
    public ApiResponse<AuthenticationResponse> register(@RequestBody SignUpRequest request) {
        try {
            AuthenticationResponse authResponse = authenticationService.register(request);
            return ApiResponse.<AuthenticationResponse>builder()
                    .code(HttpStatus.OK.value())
                    .message("User registered successfully")
                    .data(authResponse)
                    .build();
        } catch (AppException e) {
            return ApiResponse.<AuthenticationResponse>builder()
                    .code(e.getErrorCode().getStatusCode().value())
                    .message(e.getMessage())
                    .build();
        } catch (RuntimeException e) {
            return ApiResponse.<AuthenticationResponse>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An error occurred while registering the user")
                    .build();
        }
    }
    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        try {
            AuthenticationResponse authResponse = authenticationService.login(request);
            return ApiResponse.<AuthenticationResponse>builder()
                    .code(HttpStatus.OK.value())
                    .message("Login successful")
                    .data(authResponse)
                    .build();
        } catch (AppException e) {
            return ApiResponse.<AuthenticationResponse>builder()
                    .code(e.getErrorCode().getStatusCode().value())
                    .message(e.getMessage())
                    .build();
        } catch (RuntimeException e) {
            return ApiResponse.<AuthenticationResponse>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An error occurred during login")
                    .build();
        }
    }
    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) {
        try {
            IntrospectResponse introspectResponse = authenticationService.introspect(request);
            return ApiResponse.<IntrospectResponse>builder()
                    .code(HttpStatus.OK.value())
                    .message("Token introspection successful")
                    .data(introspectResponse)
                    .build();
        } catch (JOSEException e) {
            return ApiResponse.<IntrospectResponse>builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("Invalid token")
                    .build();
        } catch (RuntimeException | ParseException e) {
            return ApiResponse.<IntrospectResponse>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An error occurred during token introspection")
                    .build();
        }
    }
    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody LogoutRequest request) {
        try {
            authenticationService.logout(request);
            return ApiResponse.<Void>builder()
                    .code(HttpStatus.OK.value())
                    .message("Logout successful")
                    .build();
        } catch (RuntimeException e) {
            return ApiResponse.<Void>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An error occurred during logout")
                    .build();
        }
    }

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOTP(@RequestParam String email) {
        try {
            authenticationService.sendOTPActiveAccount(email);  // Gọi service tạo và gửi OTP
            return ResponseEntity.ok("OTP has been sent to " + email);
        } catch (AppException e) {
            return ResponseEntity.status(404).body("User not found.");
        } catch (MessagingException | jakarta.mail.MessagingException e) {
            return ResponseEntity.status(500).body("Failed to send OTP email.");
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOTP(@RequestParam String email, @RequestParam String otp) {
        try {
            boolean result = authenticationService.verifyOTPActiveAccount(email, otp); // Gọi service xác thực OTP
            if (result) {
                return ResponseEntity.ok("OTP verified successfully");
            } else {
                return ResponseEntity.status(400).body("OTP verification failed");
            }
        } catch (AppException e) {
            return ResponseEntity.status(404).body("User not found.");
        }
    }
}
