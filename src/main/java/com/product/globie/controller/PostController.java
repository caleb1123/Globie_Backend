package com.product.globie.controller;

import com.product.globie.payload.DTO.PostDTO;
import com.product.globie.payload.DTO.PostImageDTO;
import com.product.globie.payload.DTO.ProductImageDTO;
import com.product.globie.payload.request.CreatePostRequest;
import com.product.globie.payload.request.UpdatePostRequest;
import com.product.globie.payload.response.ApiResponse;
import com.product.globie.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("${api.version}/post")
@Slf4j
public class PostController {
    @Autowired
    PostService postService;



    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<PostDTO>>> getAllPost() {
        List<PostDTO> postDTOS = postService.getAllPost();
        ApiResponse<List<PostDTO>> response = ApiResponse.<List<PostDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched post")
                .data(postDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/true")
    public ResponseEntity<ApiResponse<List<PostDTO>>> getAllPostStatusTrue() {
        List<PostDTO> postDTOS = postService.getAllPostStatusTrue();
        ApiResponse<List<PostDTO>> response = ApiResponse.<List<PostDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched post")
                .data(postDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/false")
    public ResponseEntity<ApiResponse<List<PostDTO>>> getAllPostStatusFalse() {
        List<PostDTO> postDTOS = postService.getAllPostStatusFalse();
        ApiResponse<List<PostDTO>> response = ApiResponse.<List<PostDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched post")
                .data(postDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all_of_staff")
    public ResponseEntity<ApiResponse<List<PostDTO>>> getPostByStaff() {
        List<PostDTO> postDTOS = postService.getPostByStaff();
        ApiResponse<List<PostDTO>> response = ApiResponse.<List<PostDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched post")
                .data(postDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{cId}")
    public ResponseEntity<ApiResponse<List<PostDTO>>> getPostByCategoryId(@PathVariable int cId) {
        List<PostDTO> postDTOS = postService.getPostByCategory(cId);
        ApiResponse<List<PostDTO>> response = ApiResponse.<List<PostDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched post")
                .data(postDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ApiResponse<PostDTO>> getPostDetail(@PathVariable int id){
        PostDTO postDTO = postService.getPostDetail(id);
        ApiResponse<PostDTO> response = ApiResponse.<PostDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched post")
                .data(postDTO)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PostDTO>> createPost(@RequestBody CreatePostRequest createPostRequest) {
        PostDTO postDTO = postService.createPost(createPostRequest);
        ApiResponse<PostDTO> response = ApiResponse.<PostDTO>builder()
                .code(HttpStatus.CREATED.value())
                .message("Successfully created post")
                .data(postDTO)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deletePost(@PathVariable int id) {
        postService.deletePost(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully deleted post")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<PostDTO>> UpdatePost(@RequestBody UpdatePostRequest productRequest, @PathVariable int id) {
        PostDTO postDTO = postService.updatePost(productRequest, id);
        ApiResponse<PostDTO> response = ApiResponse.<PostDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully updated post")
                .data(postDTO)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update_status/{id}")
    public ResponseEntity<ApiResponse<String>> UpdateStatusPost(@PathVariable int id) {
        postService.updateStatusPost(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully updated post status")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/uploadImages/{postId}")
    public ResponseEntity<ApiResponse<List<PostImageDTO>>> uploadPostImages(@RequestParam("files") MultipartFile[] files,
                                                                                  @PathVariable int postId) throws IOException {
        List<PostImageDTO> postImageDTOS = postService.uploadMultiplePostImages(files, postId);

        ApiResponse<List<PostImageDTO>> response = ApiResponse.<List<PostImageDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully uploaded Post Images")
                .data(postImageDTOS)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all_images/{postId}")
    public ResponseEntity<ApiResponse<List<PostImageDTO>>> getAllImageByPost(@PathVariable int postId) {
        List<PostImageDTO> postImageDTOS = postService.getAllImageByPost(postId);
        ApiResponse<List<PostImageDTO>> response = ApiResponse.<List<PostImageDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Post Images")
                .data(postImageDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all_images_statusTrue/{postId}")
    public ResponseEntity<ApiResponse<List<PostImageDTO>>> getAllImageByPostStatusTrue(@PathVariable int postId) {
        List<PostImageDTO> postImageDTOS = postService.getAllImageByPostStatusTrue(postId);
        ApiResponse<List<PostImageDTO>> response = ApiResponse.<List<PostImageDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Post Images")
                .data(postImageDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteImage/{imageCode}")
    public ResponseEntity<ApiResponse<String>> deletePostImage(@PathVariable String imageCode) throws IOException {
        postService.deletePostImage(imageCode);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully Deleted Post Image")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getImage/{imageCode}")
    public ResponseEntity<ApiResponse<PostImageDTO>> getProductImageByCode(@PathVariable String imageCode){
        PostImageDTO postImageDTO = postService.getPostImageByCode(imageCode);
        ApiResponse<PostImageDTO> response = ApiResponse.<PostImageDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully Deleted Post Image")
                .data(postImageDTO)
                .build();
        return ResponseEntity.ok(response);
    }

}
