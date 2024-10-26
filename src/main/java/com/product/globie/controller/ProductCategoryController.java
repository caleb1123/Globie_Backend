package com.product.globie.controller;

import com.product.globie.payload.DTO.ProductCategoryDTO;
import com.product.globie.payload.DTO.ProductDTO;
import com.product.globie.payload.request.ProductCategoryRequest;
import com.product.globie.payload.response.ApiResponse;
import com.product.globie.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.version}/product_category")
@Slf4j
@CrossOrigin("https://globie-front-9hx0i0h1i-dolakiens-projects.vercel.app")
public class ProductCategoryController {
    @Autowired
    ProductCategoryService productCategoryService;



    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ProductCategoryDTO>>> getAllProductCategory() {
        List<ProductCategoryDTO> productCategoryDTOS = productCategoryService.getAllProductCategory();
        ApiResponse<List<ProductCategoryDTO>> response = ApiResponse.<List<ProductCategoryDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched product category")
                .data(productCategoryDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ProductCategoryDTO>> createProductCategory(@RequestBody ProductCategoryRequest productCategoryRequest) {
        ProductCategoryDTO productCategoryDTO = productCategoryService.createProductCategory(productCategoryRequest);
        ApiResponse<ProductCategoryDTO> response = ApiResponse.<ProductCategoryDTO>builder()
                .code(HttpStatus.CREATED.value())
                .message("Successfully created product category")
                .data(productCategoryDTO)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProductCategory(@PathVariable int id) {
        productCategoryService.deleteProductCategory(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully deleted product category")
                .build();
        return ResponseEntity.ok(response);
    }



}
