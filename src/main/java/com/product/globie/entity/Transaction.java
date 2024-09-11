package com.product.globie.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Table(name = "Transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transactionId")
    private int transactionId;

    @Column(name = "transaction_code", nullable = false)
    private String transactionCode;

    @Column(name = "transaction_date", nullable = false)
    private Date transactionDate;

    @Column(name = "description")
    private String description;

    @Column(name = "amount", nullable = false)
    private Double amount;;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "order_code")
    private Order order;

}
