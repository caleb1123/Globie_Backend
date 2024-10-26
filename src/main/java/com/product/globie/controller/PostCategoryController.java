package com.product.globie.controller;

import com.product.globie.payload.DTO.PostCategoryDTO;
import com.product.globie.payload.DTO.ProductCategoryDTO;
import com.product.globie.payload.request.CreatePostCategoryRequest;
import com.product.globie.payload.request.ProductCategoryRequest;
import com.product.globie.payload.response.ApiResponse;
import com.product.globie.service.PostCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.version}/post_category")
@Slf4j
public class PostCategoryController {
    @Autowired
    PostCategoryService postCategoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<PostCategoryDTO>>> getAllPostCategory() {
        List<PostCategoryDTO> postCategoryDTOS = postCategoryService.getAllPostCategory();
        ApiResponse<List<PostCategoryDTO>> response = ApiResponse.<List<PostCategoryDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched post category")
                .data(postCategoryDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PostCategoryDTO>> createPostCategory(@RequestBody CreatePostCategoryRequest postCategoryRequest) {
        PostCategoryDTO postCategory = postCategoryService.createPostCategory(postCategoryRequest);
        ApiResponse<PostCategoryDTO> response = ApiResponse.<PostCategoryDTO>builder()
                .code(HttpStatus.CREATED.value())
                .message("Successfully created post category")
                .data(postCategory)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deletePostCategory(@PathVariable int id) {
        postCategoryService.deletePostCategory(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully deleted post category")
                .build();
        return ResponseEntity.ok(response);
    }

}
