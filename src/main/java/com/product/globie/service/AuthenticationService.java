package com.product.globie.service;

import com.nimbusds.jose.JOSEException;
import com.product.globie.payload.request.IntrospectRequest;
import com.product.globie.payload.response.AuthenticationResponse;
import com.product.globie.payload.request.AuthenticationRequest;
import com.product.globie.payload.request.SignUpRequest;
import com.product.globie.payload.response.IntrospectResponse;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse login(AuthenticationRequest request);
    AuthenticationResponse register(SignUpRequest request);
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
}
