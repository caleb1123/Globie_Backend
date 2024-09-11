package com.product.globie.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;


@Entity
@Table(name = "AttributeValue")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttributeValues {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attribute_value_id")
    private int attributeValueId;

    @Column(name = "value")
    private String value;

    @OneToMany(mappedBy = "attributeValues")
    private Collection<ProductValue> productValues;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;

}
