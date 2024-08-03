package com.product.globie.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "Product_PC")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pcDetailId;
    @ManyToOne
    @JoinColumn(name = "pcCode")
    private PC pc;
    @ManyToOne
    @JoinColumn(name = "productCode")
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

}
