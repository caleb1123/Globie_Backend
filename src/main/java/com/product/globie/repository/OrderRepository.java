package com.product.globie.repository;

import com.product.globie.entity.Order;
import com.product.globie.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("select o from Order o where o.orderId = :code")
    Order findOrderByOrderCode(@Param("code") int code);

    @Query("select o from Order o where o.user.userId = :id")
    List<Order> findOrderByUser(@Param("id") int id);

    @Query(value = "SELECT \n" +
            "    SUM(total_amount) AS TotalRevenue\n" +
            "FROM \n" +
            "    orders\n" +
            "WHERE \n" +
            "    status = 'SHIPPING'\n" +
            "    AND CONVERT(DATE, order_date) = CONVERT(DATE, GETDATE());", nativeQuery = true)
    Integer countOrderByDay();

    @Query(value = "SELECT " +
            "    SUM(total_amount) AS TotalRevenue " +
            "FROM " +
            "    orders " +
            "WHERE " +
            "    status = 'SHIPPING' " +
            "    AND YEAR(order_date) = YEAR(GETDATE());", nativeQuery = true)
    Integer countOrderByYear();

    @Query(value = "SELECT \n" +
            "    COUNT(*) AS count_shipping\n" +
            "FROM \n" +
            "    orders\n" +
            "WHERE \n" +
            "    status = 'SHIPPING';\n", nativeQuery = true)
    Integer countOrderShipping();

    @Query(value = "SELECT \n" +
            "    COUNT(*) AS count_shipping\n" +
            "FROM \n" +
            "    orders\n" +
            "WHERE \n" +
            "    status = 'CANCELLED';\n", nativeQuery = true)
    Integer countOrderCancel();

    @Query(value = "SELECT \n" +
            "    COUNT(*) AS count_shipping\n" +
            "FROM \n" +
            "    orders\n" +
            "WHERE \n" +
            "    status = 'PENDING';\n", nativeQuery = true)
    Integer countOrderPending();

}
