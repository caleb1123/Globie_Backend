package com.product.globie.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;


@Entity
@Table(name = "Orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "order_code", nullable = false)
    private String orderCode;

    @Column(name = "order_date", nullable = false)
    private Date orderDate;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "total_transaction", nullable = false)
    private Integer totalTransaction;

    @Column(name = "status")
    private boolean status;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "order_name")
    private String orderName;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order")
    private Collection<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "order")
    private Collection<Payment> payments;

    @OneToMany(mappedBy = "order")
    private Collection<Transaction> transactions;
}
