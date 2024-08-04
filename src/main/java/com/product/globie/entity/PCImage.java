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
public class PCImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pcImageId;
    @Column(nullable = false, unique = true)
    private String pcImageCode;
    @Column
    private String imagePath;
    @Column
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "pcId")
    private PC pc;
}
