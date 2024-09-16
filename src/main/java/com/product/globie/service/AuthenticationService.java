package com.product.globie.service;

import com.nimbusds.jose.JOSEException;
import com.product.globie.payload.request.IntrospectRequest;
import com.product.globie.payload.request.LogoutRequest;
import com.product.globie.payload.response.AuthenticationResponse;
import com.product.globie.payload.request.AuthenticationRequest;
import com.product.globie.payload.request.SignUpRequest;
import com.product.globie.payload.response.IntrospectResponse;
import jakarta.mail.MessagingException;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse login(AuthenticationRequest request);

    AuthenticationResponse register(SignUpRequest request);

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    void logout(LogoutRequest logoutRequest);

    void sendOTPActiveAccount(String email) throws MessagingException;

    boolean verifyOTPActiveAccount(String email, String otp);
}
