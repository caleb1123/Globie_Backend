package com.product.globie.repository;

import com.product.globie.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query("select p from Payment p where p.order.orderId = :id")
    List<Payment> findByOrderId(@Param("id") int id);

    @Query("SELECT p from Payment p where p.paymentCode = :code")
    Payment findPaymentByPaymentCode(@Param("code") String code);
}
