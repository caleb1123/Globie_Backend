package com.product.globie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@Entity
@Table(name = "UserMember")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userMemberId ;

    @Column
    private LocalDate memberStartDate;

    @Column
    private LocalDate memberEndDate;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "memberLevelId")
    private MemberLevel memberLevel;
}
