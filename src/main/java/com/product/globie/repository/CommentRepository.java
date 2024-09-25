package com.product.globie.repository;

import com.product.globie.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("SELECT c from Comment c where c.post.postId = :id")
    List<Comment> getCommentByPost(@Param("id") int id);
}
