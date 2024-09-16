package com.product.globie.repository;

import com.product.globie.entity.OTPToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTPToken, Integer> {

    Optional<OTPToken> findByEmail(String email);



}
