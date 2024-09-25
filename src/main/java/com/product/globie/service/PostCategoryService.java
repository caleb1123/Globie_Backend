package com.product.globie.service;

import com.product.globie.payload.DTO.PostCategoryDTO;
import com.product.globie.payload.DTO.ProductCategoryDTO;
import com.product.globie.payload.request.CreatePostCategoryRequest;
import com.product.globie.payload.request.ProductCategoryRequest;

import java.util.List;

public interface PostCategoryService {
    List<PostCategoryDTO> getAllPostCategory();

    PostCategoryDTO createPostCategory(CreatePostCategoryRequest postCategoryRequest);

    void deletePostCategory(int id);
}
