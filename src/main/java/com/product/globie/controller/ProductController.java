package com.product.globie.controller;

import com.product.globie.entity.Product;
import com.product.globie.payload.DTO.AccountDTO;
import com.product.globie.payload.DTO.ProductDTO;
import com.product.globie.payload.request.ProductRequest;
import com.product.globie.payload.response.ApiResponse;
import com.product.globie.payload.response.ProductResponse;
import com.product.globie.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.version}/product")
@Slf4j
@CrossOrigin("http://localhost:3000")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProduct() {
        List<ProductDTO> productDTOList = productService.getAllProduct();
        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched product")
                .data(productDTOList)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/true")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProductStatusTrue() {
        List<ProductDTO> productDTOList = productService.getAllProductStatusTrue();
        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched product")
                .data(productDTOList)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/false")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProductStatusFalse() {
        List<ProductDTO> productDTOList = productService.getAllProductStatusflase();
        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched product")
                .data(productDTOList)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getProductByUserId(@PathVariable int userId) {
        List<ProductDTO> productDTOList = productService.getProductByUser(userId);
        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched product")
                .data(productDTOList)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{cId}")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getProductByCategoryId(@PathVariable int cId) {
        List<ProductDTO> productDTOList = productService.getProductByCategory(cId);
        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched product")
                .data(productDTOList)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductDetail(@PathVariable int id){
        ProductDTO product = productService.getProductDetail(id);
        ApiResponse<ProductDTO> response = ApiResponse.<ProductDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched product")
                .data(product)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct(@RequestBody ProductRequest productRequest) {
        ProductDTO productDTO = productService.createProduct(productRequest);
        ApiResponse<ProductDTO> response = ApiResponse.<ProductDTO>builder()
                .code(HttpStatus.CREATED.value())
                .message("Successfully created product")
                .data(productDTO)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        ApiResponse<ProductDTO> response = ApiResponse.<ProductDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully deleted product")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> UpdateProduct(@RequestBody ProductRequest productRequest, @PathVariable int id) {
        ProductDTO productDTO = productService.updateProduct(productRequest, id);
        ApiResponse<ProductDTO> response = ApiResponse.<ProductDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully updated product")
                .data(productDTO)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update_status/{id}")
    public ResponseEntity<ApiResponse<?>> UpdateStatusProduct(@PathVariable int id) {
        productService.updateStatusProduct(id);
        ApiResponse<ProductDTO> response = ApiResponse.<ProductDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully updated product status")
                .build();
        return ResponseEntity.ok(response);    }

}
