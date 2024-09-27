package com.product.globie.service;

import com.product.globie.payload.DTO.ProductDTO;
import com.product.globie.payload.request.CreateProductRequest;
import com.product.globie.payload.request.UpdatePostRequest;
import com.product.globie.payload.request.UpdateProductRequest;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProduct();

    List<ProductDTO> getProductByUser(int uId);

    List<ProductDTO> getAllProductStatusSelling();

    List<ProductDTO> getAllProductStatusSold();

    List<ProductDTO> getAllProductStatusProcessing();

    ProductDTO createProduct(CreateProductRequest productRequest);

    void deleteProduct(int pId);

    ProductDTO updateProduct(UpdateProductRequest productRequest, int pId);

    void updateStatusProduct(int pId);

    ProductDTO getProductDetail(int pId);

    List<ProductDTO> getProductByCategory(int cId);


}
