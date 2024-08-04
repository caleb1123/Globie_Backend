package com.product.globie.controller;

import com.product.globie.exception.AppException;
import com.product.globie.payload.request.AuthenticationRequest;
import com.product.globie.payload.response.ApiResponse;
import com.product.globie.payload.response.AuthenticationResponse;
import com.product.globie.payload.request.SignUpRequest;
import com.product.globie.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authen")
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
}
