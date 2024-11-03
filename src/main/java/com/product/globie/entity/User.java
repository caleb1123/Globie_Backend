package com.product.globie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "nvarchar(255)")
    private String fullName;

    @Column
    private String avatar;

    @Column
    private Date dob;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String sex;

    @Column
    private String address;

    @Column
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;

    @OneToOne(mappedBy = "user")
    private Wallet wallet;

    @OneToOne(mappedBy = "user")
    private UserMember userMember;

    @OneToMany(mappedBy = "user")
    private Collection<Post> posts;

    @OneToMany(mappedBy = "user")
    private Collection<Rate> rates;

    @OneToMany(mappedBy = "user")
    private Collection<Transaction> transactions;

    @OneToMany(mappedBy = "user")
    private Collection<ShippingAddress> shippingAddresses;
}
