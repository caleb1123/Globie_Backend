package com.product.globie.repository;

import com.product.globie.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RateRepository extends JpaRepository<Rate, Integer> {
    List<Rate> findRatesByProduct_ProductId(int productId);
    List<Rate> findRatesByUser_UserId(int userId);

}
