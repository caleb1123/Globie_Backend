package com.product.globie.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Table(name = "Attribute")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attributeId;

    @Column(nullable = false)
    private String attributeName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "attribute")
    private Collection<AttributeValues> attributeValues;
}
