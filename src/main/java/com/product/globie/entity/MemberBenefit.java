package com.product.globie.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "MemberBenefit")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberBenefit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int benefitId;

    @Column
    private String benefitDescription;

    @ManyToOne
    @JoinColumn(name = "memberLevelId", nullable = false)
    private MemberLevel memberLevel;
}
