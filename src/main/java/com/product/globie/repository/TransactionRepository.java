package com.product.globie.repository;

import com.product.globie.entity.Product;
import com.product.globie.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query("SELECT t FROM Transaction t WHERE "
            + "(:startDate IS NULL OR t.transactionDate >= :startDate) "
            + "AND (:endDate IS NULL OR t.transactionDate <= :endDate)")
    List<Transaction> filterTransaction(@Param("startDate") Date startDate,
                                        @Param("endDate") Date endDate);

}
