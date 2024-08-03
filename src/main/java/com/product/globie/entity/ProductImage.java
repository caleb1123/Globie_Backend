package com.product.globie.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "Role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productImageId;

    @Column(name = "ProdcutImageCode", nullable = false, unique = true)
    private String productImageCode;

    @Column(name = "imagePath", nullable = false)
    private String imagePath;

    @Column(name = "status")
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
}
