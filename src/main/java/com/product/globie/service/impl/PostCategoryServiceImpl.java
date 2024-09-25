package com.product.globie.service.impl;

import com.product.globie.entity.PostCategory;
import com.product.globie.entity.ProductCategory;
import com.product.globie.payload.DTO.PostCategoryDTO;
import com.product.globie.payload.DTO.ProductCategoryDTO;
import com.product.globie.payload.request.CreatePostCategoryRequest;
import com.product.globie.repository.PostCategoryRepository;
import com.product.globie.service.PostCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostCategoryServiceImpl implements PostCategoryService {
    @Autowired
    PostCategoryRepository postCategoryRepository;

    @Autowired
    ModelMapper mapper;


    @Override
    public List<PostCategoryDTO> getAllPostCategory() {
        List<PostCategory> postCategories = postCategoryRepository.findAll();

        return postCategories.stream()
                .map(category -> mapper.map(category, PostCategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostCategoryDTO createPostCategory(CreatePostCategoryRequest postCategoryRequest) {
        PostCategory postCategory = new PostCategory();
        postCategory.setCategoryName(postCategoryRequest.getCategoryName());
        postCategory.setStatus(true);

        postCategoryRepository.save(postCategory);

        return mapper.map(postCategory, PostCategoryDTO.class);
    }

    @Override
    public void deletePostCategory(int id) {
        PostCategory postCategory = postCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post Category not Found with Id: " + id));

        postCategoryRepository.delete(postCategory);
    }
}
