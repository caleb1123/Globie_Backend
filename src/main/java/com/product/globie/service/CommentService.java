package com.product.globie.service;

import com.product.globie.entity.Comment;
import com.product.globie.payload.DTO.CommentDTO;
import com.product.globie.payload.request.CommentRequest;
import com.product.globie.payload.request.UpdateCommentRequest;

import java.util.List;

public interface CommentService {
    List<CommentDTO> getAllCommentOfPost(int pId);

    void addComment(CommentRequest commentRequest);

    void updateComment(UpdateCommentRequest commentRequest, int id);

    void deleteComment(int cId);
}
