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
@CrossOrigin("http://localhost:3000")
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

    @GetMapping("/all/true")
    public ResponseEntity<ApiResponse<List<ProductCategoryDTO>>> getAllProductCategoryStatusTrue() {
        List<ProductCategoryDTO> productCategoryDTOS = productCategoryService.getProductCategoryStatusTrue();
        ApiResponse<List<ProductCategoryDTO>> response = ApiResponse.<List<ProductCategoryDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched product category")
                .data(productCategoryDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/false")
    public ResponseEntity<ApiResponse<List<ProductCategoryDTO>>> getAllProductCategoryStatusFalse() {
        List<ProductCategoryDTO> productCategoryDTOS = productCategoryService.getProductCategoryStatusFalse();
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

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<ProductCategoryDTO>> createProductCategory(@RequestBody ProductCategoryRequest productCategoryRequest, @PathVariable int id) {
        ProductCategoryDTO productCategoryDTO = productCategoryService.updateProductCategory(productCategoryRequest, id);
        ApiResponse<ProductCategoryDTO> response = ApiResponse.<ProductCategoryDTO>builder()
                .code(HttpStatus.CREATED.value())
                .message("Successfully updated product category")
                .data(productCategoryDTO)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update_status/{id}")
    public ResponseEntity<ApiResponse<ProductCategoryDTO>> updateStatusProductCategory(@PathVariable int id) {
        productCategoryService.updateStatusProductCategory(id);
        ApiResponse<ProductCategoryDTO> response = ApiResponse.<ProductCategoryDTO>builder()
                .code(HttpStatus.CREATED.value())
                .message("Successfully updated product category status")
                .build();
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<ProductCategoryDTO>> deleteStatusProductCategory(@PathVariable int id) {
        productCategoryService.deleteProductCategory(id);
        ApiResponse<ProductCategoryDTO> response = ApiResponse.<ProductCategoryDTO>builder()
                .code(HttpStatus.CREATED.value())
                .message("Successfully deleted product category")
                .build();
        return ResponseEntity.ok(response);
    }



}
