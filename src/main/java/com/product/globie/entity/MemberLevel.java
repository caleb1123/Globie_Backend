package com.product.globie.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;


@Entity
@Table(name = "MemberLevel")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberLevelId;

    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String levelName;

    @Column(columnDefinition = "nvarchar(255)")
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer durationInMonths;

    @OneToMany(mappedBy = "memberLevel")
    private Collection<UserMember> userMembers;

}
