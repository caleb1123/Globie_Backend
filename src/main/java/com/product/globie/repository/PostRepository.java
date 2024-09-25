package com.product.globie.repository;

import com.product.globie.entity.Post;
import com.product.globie.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("SELECT p FROM Post p WHERE p.user.userId = :uId")
    List<Post> findPostByUser(@Param("uId") int uId);

    @Query("SELECT p FROM Post p WHERE p.postCategory.postCategoryId = :cId")
    List<Post> findPostByCategory(@Param("cId") int cId);
}
