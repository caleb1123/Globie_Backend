package com.product.globie.repository;

import com.product.globie.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    @Query(value = "SELECT COUNT(*)" +
            "FROM report" +
           " WHERE status = 1" +
            " AND product_id = :id", nativeQuery = true)
    int countReportByStatusTrue(@Param("id") int id);
}
