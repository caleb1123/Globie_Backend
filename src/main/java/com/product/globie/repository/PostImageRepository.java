package com.product.globie.repository;

import com.product.globie.entity.PostImage;
import com.product.globie.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostImageRepository extends JpaRepository<PostImage, Integer> {
    @Query("select p from PostImage p where p.post.postId = :pId")
    Optional<List<PostImage>> getPostImageByPostId(@Param("pId") int id);

    @Query("select p from PostImage p WHERE p.postImageCode = :code")
    Optional<PostImage> getPostImageByImageCode(@Param("code") String code);

    @Query(value = "SELECT COUNT(post_image_id) AS image_count FROM post_image where post_id = :id and status = 1 GROUP BY post_id",nativeQuery = true)
    Integer countImageByPost(int id);
}
