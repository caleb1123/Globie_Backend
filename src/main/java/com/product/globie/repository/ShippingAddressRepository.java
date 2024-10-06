package com.product.globie.repository;

import com.product.globie.entity.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Integer> {
    @Query(value = "select s from ShippingAddress s where s.user.userId = :id")
    List<ShippingAddress> findShippingAddressByUser(@Param("id") int id);
}
