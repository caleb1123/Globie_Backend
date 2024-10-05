package com.product.globie.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Table(name = "Payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private int paymentId;

    @Column(name = "payment_code", nullable = false)
    private String paymentCode;

    @Column(name = "payment_date", nullable = false)
    private Date paymentDate;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "payment_amount", nullable = false)
    private Double paymentAmount;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "order_code")
    private Order order;
}
