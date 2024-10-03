package com.product.globie.service;

import com.product.globie.payload.DTO.PostDTO;
import com.product.globie.payload.DTO.PostImageDTO;
import com.product.globie.payload.DTO.ProductImageDTO;
import com.product.globie.payload.request.CreatePostRequest;
import com.product.globie.payload.request.UpdatePostRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    List<PostImageDTO> uploadMultiplePostImages(MultipartFile[] multipartFiles, int postId) throws IOException;

    List<PostImageDTO> getAllImageByPost(int postId);

    List<PostImageDTO> getAllImageByPostStatusTrue(int postId);

    void deletePostImage(String imageCode) throws IOException;

    PostImageDTO getPostImageByCode(String imageCode);

}
