package com.product.globie.service;

import com.product.globie.entity.Product;
import com.product.globie.payload.DTO.ProductDTO;
import com.product.globie.payload.request.ProductRequest;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProduct();

    List<ProductDTO> getProductByUser(int uId);

    List<ProductDTO> getAllProductStatusTrue();

    List<ProductDTO> getAllProductStatusflase();

    ProductDTO createProduct(ProductRequest productRequest);

    void deleteProduct(int pId);

    ProductDTO updateProduct(ProductRequest productRequest, int pId);

    void updateStatusProduct(int pId);

    ProductDTO getProductDetail(int pId);

    List<ProductDTO> getProductByCategory(int cId);


}
