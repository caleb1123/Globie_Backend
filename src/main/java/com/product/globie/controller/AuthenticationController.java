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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
