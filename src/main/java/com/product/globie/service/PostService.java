package com.product.globie.service;

import com.product.globie.payload.DTO.PostDTO;
import com.product.globie.payload.request.CreatePostRequest;
import com.product.globie.payload.request.UpdatePostRequest;

import java.util.List;

public interface PostService {
    List<PostDTO> getAllPost();

    List<PostDTO> getPostByStaff();

    List<PostDTO> getAllPostStatusTrue();

    List<PostDTO> getAllPostStatusFalse();

    PostDTO createPost(CreatePostRequest postRequest);

    void deletePost(int pId);

    PostDTO updatePost(UpdatePostRequest postRequest, int pId);

    void updateStatusPost(int pId);

    PostDTO getPostDetail(int pId);

    List<PostDTO> getPostByCategory(int cId);
}
