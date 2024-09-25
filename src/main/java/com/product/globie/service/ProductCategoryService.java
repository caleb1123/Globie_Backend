package com.product.globie.service;

import com.product.globie.entity.ProductCategory;
import com.product.globie.payload.DTO.ProductCategoryDTO;
import com.product.globie.payload.request.ProductCategoryRequest;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategoryDTO> getAllProductCategory();

    ProductCategoryDTO createProductCategory(ProductCategoryRequest productCategoryRequest);

    void deleteProductCategory(int id);

}
