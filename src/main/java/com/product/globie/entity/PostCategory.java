package com.product.globie.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "Post_Category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postCategoryId;
    @Column(name = "categoryName", nullable = false, unique = true)
    private String categoryName;
    @Column(name = "status")
    private boolean status;
}
