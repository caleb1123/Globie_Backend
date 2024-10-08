package com.product.globie.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "PostImage")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postImageId;

    @Column(nullable = false)
    private String imagePath;

    @Column
    private boolean status;

    @Column
    private String postImageCode;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;
}
