package com.product.globie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Table(name = "ProductCategory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productCategoryId;

    @Column(nullable = false, unique = true, columnDefinition = "nvarchar(255)")
    private String categoryName;

    @Column(columnDefinition = "nvarchar(255)")
    private String description;

    @Column
    private boolean status;

    @OneToMany(mappedBy = "productCategory")
    private Collection<Product> products;

    @OneToMany(mappedBy = "productCategory")
    private Collection<Attribute> attributes;

}
