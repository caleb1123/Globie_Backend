package com.product.globie.service;

import com.product.globie.entity.ProductCategory;
import com.product.globie.payload.DTO.ProductCategoryDTO;
import com.product.globie.payload.request.ProductCategoryRequest;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategoryDTO> getAllProductCategory();

    List<ProductCategoryDTO> getProductCategoryStatusTrue();

    List<ProductCategoryDTO> getProductCategoryStatusFalse();

    ProductCategoryDTO createProductCategory(ProductCategoryRequest productCategoryRequest);

    ProductCategoryDTO updateProductCategory(ProductCategoryRequest productCategoryRequest, int id);

    void deleteProductCategory(int id);

    void updateStatusProductCategory(int id);

}
