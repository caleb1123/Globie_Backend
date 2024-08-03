package com.product.globie.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "Post")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @Column(name = "postTitle", nullable = false)
    private String postTitle;

    @Column(name = "postContent", nullable = false)
    private String postContent;

    @Column(name = "status")
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "postCategoryId")
    private PostCategory postCategory;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;

    @OneToMany(mappedBy = "post")
    private Collection<PostImage> postImages;


}
