package com.product.globie.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

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

    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String postTitle;

    @Column(nullable = false, columnDefinition = "nvarchar(max)")
    private String postContent;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "updated_time")
    private Date updatedTime;

    @Column
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "postCategoryId")
    private PostCategory postCategory;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "post")
    private Collection<PostImage> postImages;

    @OneToMany(mappedBy = "post")
    private Collection<Comment> comments;
}
