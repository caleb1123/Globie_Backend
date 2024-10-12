package com.product.globie.repository;

import com.product.globie.entity.Order;
import com.product.globie.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("select o from Order o where o.orderId = :code")
    Order findOrderByOrderCode(@Param("code") int code);

    @Query("select o from Order o where o.user.userId = :id")
    List<Order> findOrderByUser(@Param("id") int id);
}
