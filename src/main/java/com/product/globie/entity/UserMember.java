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

    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String storeName;

    @Column(nullable = false, columnDefinition = "nvarchar(max)")
    private String description;

    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String storeAddress;

    @Column(nullable = false)
    private String storePhone;

    @Column
    private LocalDate memberStartDate;

    @Column
    private LocalDate memberEndDate;

    @Column(nullable = false)
    private boolean status;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "memberLevelId")
    private MemberLevel memberLevel;

    @OneToOne
    @JoinColumn(name = "orderId")
    private Order order;
}
