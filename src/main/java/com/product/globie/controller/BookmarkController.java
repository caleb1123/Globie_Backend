package com.product.globie.controller;

import com.product.globie.payload.DTO.BookMarkDTO;
import com.product.globie.payload.DTO.CommentDTO;
import com.product.globie.payload.request.CommentRequest;
import com.product.globie.payload.request.UpdateCommentRequest;
import com.product.globie.payload.response.ApiResponse;
import com.product.globie.service.BookMarkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.version}/bookmark")
@Slf4j
@CrossOrigin("https://globie-front-9hx0i0h1i-dolakiens-projects.vercel.app")
public class BookmarkController {
    @Autowired
    BookMarkService bookMarkService;


    @GetMapping("/all_of_user")
    public ResponseEntity<ApiResponse<List<BookMarkDTO>>> getAllBookMarkOfUser() {
        List<BookMarkDTO> bookMarkDTOS = bookMarkService.getAllBookMarkOfUser();
        ApiResponse<List<BookMarkDTO>> response = ApiResponse.<List<BookMarkDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Bookmark")
                .data(bookMarkDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create/{productId}")
    public ResponseEntity<ApiResponse<String>> addBookmark(@PathVariable int productId) {
        bookMarkService.addBookMark(productId);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(HttpStatus.CREATED.value())
                .message("Successfully created bookmark")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponse<String>> deleteBookmark(@PathVariable int productId) {
        bookMarkService.deleteBookMark(productId);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully deleted Bookmark")
                .build();
        return ResponseEntity.ok(response);
    }

}
