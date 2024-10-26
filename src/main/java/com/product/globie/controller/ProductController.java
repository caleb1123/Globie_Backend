package com.product.globie.controller;

import com.product.globie.payload.DTO.ProductDTO;
import com.product.globie.payload.DTO.ProductImageDTO;
import com.product.globie.payload.request.CreateProductRequest;
import com.product.globie.payload.request.UpdateProductRequest;
import com.product.globie.payload.response.ApiResponse;
import com.product.globie.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("${api.version}/product")
@Slf4j
@CrossOrigin("https://globie-front-9hx0i0h1i-dolakiens-projects.vercel.app")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProduct() {
        List<ProductDTO> productDTOList = productService.getAllProduct();
        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Product")
                .data(productDTOList)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/selling")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProductStatusSelling() {
        List<ProductDTO> productDTOList = productService.getAllProductStatusSelling();
        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Product")
                .data(productDTOList)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all_store")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProductStatusSellingStore() {
        List<ProductDTO> productDTOList = productService.getAllProductStatusSellingStore();
        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Product")
                .data(productDTOList)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all_user")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProductStatusSellingUser() {
        List<ProductDTO> productDTOList = productService.getAllProductStatusSellingUser();
        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Product")
                .data(productDTOList)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/sold")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProductStatusSold() {
        List<ProductDTO> productDTOList = productService.getAllProductStatusSold();
        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Product")
                .data(productDTOList)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/processing")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProductStatusProcessing() {
        List<ProductDTO> productDTOList = productService.getAllProductStatusProcessing();
        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Product")
                .data(productDTOList)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all_of_user_processing")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getProductByUserStatusProcessing() {
        List<ProductDTO> productDTOList = productService.getProductByUserStatusProcessing();
        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Product")
                .data(productDTOList)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all_of_user_selling")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getProductByUserStatusSelling() {
        List<ProductDTO> productDTOList = productService.getProductByUserStatusSelling();
        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Product")
                .data(productDTOList)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all_of_user_sold")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getProductByUserStatusSold() {
        List<ProductDTO> productDTOList = productService.getProductByUserStatusSold();
        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Product")
                .data(productDTOList)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> filterProducts( @RequestParam(required = false) String brand,
                                                                         @RequestParam(required = false) String origin,
                                                                         @RequestParam(required = false) Double minPrice,
                                                                         @RequestParam(required = false) Double maxPrice,
                                                                         @RequestParam(required = false) Integer categoryId) {
        List<ProductDTO> productDTOList = productService.filterProducts(categoryId, brand, origin, minPrice, maxPrice);
        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Product")
                .data(productDTOList)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> searchProducts(@RequestParam(required = false) String keyWord) {
        List<ProductDTO> productDTOList = productService.searchProductsByNameOrBrand(keyWord);
        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Product")
                .data(productDTOList)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{cId}")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getProductByCategoryId(@PathVariable int cId) {
        List<ProductDTO> productDTOList = productService.getProductByCategory(cId);
        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Product")
                .data(productDTOList)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category_store/{cId}")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getProductStoreByCategoryId(@PathVariable int cId) {
        List<ProductDTO> productDTOList = productService.getProductByCategoryOfStore(cId);
        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Product")
                .data(productDTOList)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category_user/{cId}")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getProductUserByCategoryId(@PathVariable int cId) {
        List<ProductDTO> productDTOList = productService.getProductByCategoryOfUser(cId);
        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Product")
                .data(productDTOList)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductDetail(@PathVariable int id){
        ProductDTO product = productService.getProductDetail(id);
        ApiResponse<ProductDTO> response = ApiResponse.<ProductDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Product")
                .data(product)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct(@RequestBody CreateProductRequest productRequest) {
        ProductDTO productDTO = productService.createProduct(productRequest);
        ApiResponse<ProductDTO> response = ApiResponse.<ProductDTO>builder()
                .code(HttpStatus.CREATED.value())
                .message("Successfully created Product")
                .data(productDTO)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully deleted Product")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> UpdateProduct(@RequestBody UpdateProductRequest productRequest, @PathVariable int id) {
        ProductDTO productDTO = productService.updateProduct(productRequest, id);
        ApiResponse<ProductDTO> response = ApiResponse.<ProductDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully updated Product")
                .data(productDTO)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update_status/{id}")
    public ResponseEntity<ApiResponse<String>> UpdateStatusProduct(@PathVariable int id) {
        productService.updateStatusProduct(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully updated Product status")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/uploadImages/{productId}")
    public ResponseEntity<ApiResponse<List<ProductImageDTO>>> uploadProductImages(@RequestParam("files") MultipartFile[] files,
                                                                                  @PathVariable int productId) throws IOException {
        List<ProductImageDTO> productImageDTOs = productService.uploadMultipleProductImages(files, productId);

        ApiResponse<List<ProductImageDTO>> response = ApiResponse.<List<ProductImageDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully uploaded Product Images")
                .data(productImageDTOs)
                .build();

        return ResponseEntity.ok(response);
    }


    @GetMapping("/all_images/{productId}")
    public ResponseEntity<ApiResponse<List<ProductImageDTO>>> getAllImageByProduct(@PathVariable int productId) {
        List<ProductImageDTO> productImageDTOS = productService.getAllImageByProduct(productId);
        ApiResponse<List<ProductImageDTO>> response = ApiResponse.<List<ProductImageDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Product Images")
                .data(productImageDTOS)
                .build();
        return ResponseEntity.ok(response);
    }



    @GetMapping("/all_images_statusTrue/{productId}")
    public ResponseEntity<ApiResponse<List<ProductImageDTO>>> getAllImageByProductStatusTrue(@PathVariable int productId) {
        List<ProductImageDTO> productImageDTOS = productService.getAllImageByProductStatusTrue(productId);
        ApiResponse<List<ProductImageDTO>> response = ApiResponse.<List<ProductImageDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Product Images")
                .data(productImageDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteImage/{imageCode}")
    public ResponseEntity<ApiResponse<String>> deleteProductImage(@PathVariable String imageCode) throws IOException {
        productService.deleteProductImage(imageCode);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully Deleted Product Image")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getImage/{imageCode}")
    public ResponseEntity<ApiResponse<ProductImageDTO>> getProductImageByCode(@PathVariable String imageCode){
        ProductImageDTO productImageDTO = productService.getProductImageByCode(imageCode);
        ApiResponse<ProductImageDTO> response = ApiResponse.<ProductImageDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully Deleted Product Image")
                .data(productImageDTO)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count_selling")
    public ResponseEntity<ApiResponse<Integer>> countProductSelling() {
        int count = productService.countProductSelling();
        ApiResponse<Integer> response = ApiResponse.<Integer>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Product")
                .data(count)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count_processing")
    public ResponseEntity<ApiResponse<Integer>> countProductProcessing() {
        int count = productService.countProductProcessing();
        ApiResponse<Integer> response = ApiResponse.<Integer>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Product")
                .data(count)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count_sold")
    public ResponseEntity<ApiResponse<Integer>> countProductSold() {
        int count = productService.countProductSold();
        ApiResponse<Integer> response = ApiResponse.<Integer>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Product")
                .data(count)
                .build();
        return ResponseEntity.ok(response);
    }

}
