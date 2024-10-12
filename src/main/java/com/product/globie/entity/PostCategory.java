package com.product.globie.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Table(name = "PostCategory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postCategoryId;
    @Column(nullable = false, columnDefinition = "nvarchar(50)")
    private String categoryName;
    @Column
    private boolean status;

    @OneToMany(mappedBy = "postCategory")
    private Collection<Post> posts;
}
