package com.product.globie.repository;

import com.product.globie.entity.Product;
import com.product.globie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT * from product WHERE user_id = :uId", nativeQuery = true)
    List<Product> findProductByUser(@Param("uId") int uId);

}
