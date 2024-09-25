package com.product.globie.service.impl;

import com.product.globie.entity.Product;
import com.product.globie.entity.ProductCategory;
import com.product.globie.payload.DTO.AccountDTO;
import com.product.globie.payload.DTO.ProductCategoryDTO;
import com.product.globie.payload.DTO.ProductDTO;
import com.product.globie.payload.request.ProductCategoryRequest;
import com.product.globie.repository.ProductCategoryRepository;
import com.product.globie.service.ProductCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public List<ProductCategoryDTO> getAllProductCategory() {
        List<ProductCategory> productCategories = productCategoryRepository.findAll();

        return productCategories.stream()
                .map(category -> mapper.map(category, ProductCategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductCategoryDTO createProductCategory(ProductCategoryRequest productCategoryRequest) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName(productCategoryRequest.getCategoryName());
        productCategory.setDescription(productCategoryRequest.getDescription());
        productCategory.setStatus(true);

        productCategoryRepository.save(productCategory);

        return mapper.map(productCategory, ProductCategoryDTO.class);
    }


    @Override
    public void deleteProductCategory(int id) {
        ProductCategory productCategory = productCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Category not Found with Id: " + id));

        productCategoryRepository.delete(productCategory);
    }




}
