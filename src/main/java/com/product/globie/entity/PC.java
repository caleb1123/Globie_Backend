package com.product.globie.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Table(name = "PC")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pcId;

    @Column(nullable = false, unique = true)
    private String pcCode;

    @Column(nullable = false, unique = true)
    private String pcName;

    @Column
    private String pcDescription;

    @Column(nullable = false)
    private double pcPrice;

    @Column(nullable = false)
    private int pcQuantity;

    @Column(name = "pcStatus")
    private boolean pcStatus;

    @OneToMany(mappedBy = "pc")
    private Collection<PCImage> pcImages;

    @OneToMany(mappedBy = "pc")
    private Collection<ProductPC> pcDetails;

}
