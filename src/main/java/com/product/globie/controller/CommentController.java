package com.product.globie.controller;

import com.product.globie.payload.DTO.CommentDTO;
import com.product.globie.payload.DTO.PostCategoryDTO;
import com.product.globie.payload.request.CommentRequest;
import com.product.globie.payload.request.CreatePostCategoryRequest;
import com.product.globie.payload.request.UpdateCommentRequest;
import com.product.globie.payload.response.ApiResponse;
import com.product.globie.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.version}/comment")
@Slf4j
@CrossOrigin("https://globie-front-9hx0i0h1i-dolakiens-projects.vercel.app")
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping("/all/{postId}")
    public ResponseEntity<ApiResponse<List<CommentDTO>>> getAllCommentOfPost(@PathVariable("postId") int id) {
        List<CommentDTO> commentDTOS = commentService.getAllCommentOfPost(id);
        ApiResponse<List<CommentDTO>> response = ApiResponse.<List<CommentDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Comment")
                .data(commentDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> addComment(@RequestBody CommentRequest commentRequest) {
        commentService.addComment(commentRequest);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(HttpStatus.CREATED.value())
                .message("Successfully created comment")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<String>> updateComment(@RequestBody UpdateCommentRequest commentRequest, @PathVariable int id) {
        commentService.updateComment(commentRequest, id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully update comment")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteComment(@PathVariable int id) {
        commentService.deleteComment(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully deleted comment")
                .build();
        return ResponseEntity.ok(response);
    }

}
