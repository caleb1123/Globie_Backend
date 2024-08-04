package com.product.globie.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "ProductImage")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productImageId;

    @Column(nullable = false, unique = true)
    private String productImageCode;

    @Column(nullable = false)
    private String imagePath;

    @Column
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
}
