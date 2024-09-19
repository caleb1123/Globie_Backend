package com.product.globie.service;

import com.product.globie.entity.Product;
import com.product.globie.payload.DTO.ProductDTO;
import com.product.globie.payload.request.ProductRequest;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProduct();

    ProductDTO createProduct(ProductRequest productRequest);

    void deleteProduct(int pId);

    ProductDTO updateProduct(ProductRequest productRequest, int pId);

    void updateStatusProduct(int pId);
}
