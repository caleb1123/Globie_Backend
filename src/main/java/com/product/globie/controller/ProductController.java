package com.product.globie.controller;

import com.product.globie.payload.DTO.ProductDTO;
import com.product.globie.payload.request.ProductRequest;
import com.product.globie.payload.response.ApiResponse;
import com.product.globie.payload.response.ProductResponse;
import com.product.globie.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.version}/product")
@Slf4j
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
        List<ProductDTO> productDTOList = productService.getAllProduct();
        return ResponseEntity.ok(productDTOList);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductRequest productRequest) {
        ProductDTO productDTO = productService.createProduct(productRequest);
        return ResponseEntity.ok(productDTO);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Deleted Successfully!");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDTO> UpdateProduct(@RequestBody ProductRequest productRequest, @PathVariable int id) {
        ProductDTO productDTO = productService.updateProduct(productRequest, id);
        return ResponseEntity.ok(productDTO);
    }

    @PutMapping("/update_status/{id}")
    public ResponseEntity<?> UpdateStatusProduct(@PathVariable int id) {
        productService.updateStatusProduct(id);
        return ResponseEntity.ok("Updated Successfully!");
    }

}
