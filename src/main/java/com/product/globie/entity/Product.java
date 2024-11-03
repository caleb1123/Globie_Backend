package com.product.globie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "Product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String productName;

    @Column(nullable = false, columnDefinition = "nvarchar(max)")
    private String description;

    @Column
    private String brand;

    @Column
    private String origin;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String warranty;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "updated_time")
    private Date updatedTime;

    @Column
    private String status;

    @ManyToOne
    @JoinColumn(name = "productCategoryId")
    private ProductCategory productCategory;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "product")
    private Collection<ProductImage> productImages;

    @OneToMany(mappedBy = "product")
    private Collection<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product")
    private Collection<ProductValue> productValues;

    @OneToMany(mappedBy = "product")
    private Collection<Rate> rates;

    @OneToMany(mappedBy = "product")
    private Collection<Report> reports;

    @OneToMany(mappedBy = "product")
    private Collection<Bookmark> bookmarks;
}
