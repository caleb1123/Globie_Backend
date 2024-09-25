package com.product.globie.service.impl;

import com.product.globie.config.Util;
import com.product.globie.entity.Product;
import com.product.globie.entity.ProductCategory;
import com.product.globie.entity.User;
import com.product.globie.exception.AppException;
import com.product.globie.payload.DTO.ProductDTO;
import com.product.globie.payload.request.ProductRequest;
import com.product.globie.repository.ProductCategoryRepository;
import com.product.globie.repository.ProductRepository;
import com.product.globie.repository.UserRepository;
import com.product.globie.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Util util;

    @Autowired
    ModelMapper mapper;



    @Override
    public List<ProductDTO> getAllProduct() {
        List<Product> products = productRepository.findAll();

        List<ProductDTO> productDTOS = products.stream()
                .map(product -> {
                    ProductDTO productDTO = mapper.map(product, ProductDTO.class);

                    if (product.getProductCategory() != null) {
                        productDTO.setProductCategoryId(product.getProductCategory().getProductCategoryId());
                    }
                    if (product.getUser() != null) {
                        productDTO.setUserId(product.getUser().getUserId());
                    }

                    return productDTO;
                })
                .collect(Collectors.toList());

        return productDTOS.isEmpty() ? null : productDTOS;
    }


    @Override
    public List<ProductDTO> getProductByUser(int uId) {
        List<Product> products = productRepository.findProductByUser(uId);
        if(products.isEmpty()){
            throw new RuntimeException("There are no products of this User Id: " + uId);
        }
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> {
                    ProductDTO productDTO = mapper.map(product, ProductDTO.class);

                    if (product.getProductCategory() != null) {
                        productDTO.setProductCategoryId(product.getProductCategory().getProductCategoryId());
                    }
                    if (product.getUser() != null) {
                        productDTO.setUserId(product.getUser().getUserId());
                    }

                    return productDTO;
                })
                .collect(Collectors.toList());

        return productDTOS.isEmpty() ? null : productDTOS;
    }

    @Override
    public List<ProductDTO> getAllProductStatusTrue() {
        List<Product> products = productRepository.findAll();

        List<ProductDTO> productDTOS = products.stream()
                .filter(Product :: isStatus)
                .map(product -> {
                    ProductDTO productDTO = mapper.map(product, ProductDTO.class);

                    if (product.getProductCategory() != null) {
                        productDTO.setProductCategoryId(product.getProductCategory().getProductCategoryId());
                    }
                    if (product.getUser() != null) {
                        productDTO.setUserId(product.getUser().getUserId());
                    }

                    return productDTO;
                })
                .collect(Collectors.toList());

        return productDTOS.isEmpty() ? null : productDTOS;
    }

    @Override
    public List<ProductDTO> getAllProductStatusflase() {
        List<Product> products = productRepository.findAll();

        List<ProductDTO> productDTOS = products.stream()
                .filter(Product -> !Product.isStatus())
                .map(product -> {
                    ProductDTO productDTO = mapper.map(product, ProductDTO.class);

                    if (product.getProductCategory() != null) {
                        productDTO.setProductCategoryId(product.getProductCategory().getProductCategoryId());
                    }
                    if (product.getUser() != null) {
                        productDTO.setUserId(product.getUser().getUserId());
                    }

                    return productDTO;
                })
                .collect(Collectors.toList());

        return productDTOS.isEmpty() ? null : productDTOS;
    }


    @Override
    public ProductDTO createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getDescription());
        product.setBrand(productRequest.getBrand());
        product.setOrigin(productRequest.getOrigin());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setCreatedTime(new Date());
        product.setStatus(false);
        product.setUser(util.getUserFromAuthentication());

        ProductCategory productCategory = productCategoryRepository
                .findById(productRequest.getProductCategoryId())
                .orElseThrow(() -> new RuntimeException("ProductCategory not found with Id: " + productRequest.getProductCategoryId()));
        product.setProductCategory(productCategory);

        Product savedProduct = productRepository.save(product);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(savedProduct.getProductId());
        productDTO.setProductName(savedProduct.getProductName());
        productDTO.setDescription(savedProduct.getDescription());
        productDTO.setBrand(savedProduct.getBrand());
        productDTO.setOrigin(savedProduct.getOrigin());
        productDTO.setPrice(savedProduct.getPrice());
        productDTO.setQuantity(savedProduct.getQuantity());
        productDTO.setCreatedTime(savedProduct.getCreatedTime());
        productDTO.setStatus(savedProduct.isStatus());
        productDTO.setProductCategoryId(savedProduct.getProductCategory().getProductCategoryId());
        productDTO.setUserId(savedProduct.getUser().getUserId());

        return productDTO;
    }

    @Override
    public void deleteProduct(int pId) {
        Product product = productRepository.findById(pId)
                .orElseThrow(() -> new RuntimeException("Product not Found with Id: " + pId));

        productRepository.delete(product);
    }

    @Override
    public ProductDTO updateProduct(ProductRequest productRequest, int pId) {

        Product product = productRepository.findById(pId)
                .orElseThrow(() -> new RuntimeException("Product not Found with Id: " + pId));
        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getDescription());
        product.setBrand(productRequest.getBrand());
        product.setOrigin(productRequest.getOrigin());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setUpdatedTime(new Date());

        ProductCategory productCategory = productCategoryRepository
                .findById(productRequest.getProductCategoryId())
                .orElseThrow(() -> new RuntimeException("ProductCategory not found with Id: " + productRequest.getProductCategoryId()));
        product.setProductCategory(productCategory);

        Product savedProduct = productRepository.save(product);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(savedProduct.getProductId());
        productDTO.setProductName(savedProduct.getProductName());
        productDTO.setDescription(savedProduct.getDescription());
        productDTO.setBrand(savedProduct.getBrand());
        productDTO.setOrigin(savedProduct.getOrigin());
        productDTO.setPrice(savedProduct.getPrice());
        productDTO.setQuantity(savedProduct.getQuantity());
        productDTO.setUpdatedTime(savedProduct.getUpdatedTime());
        productDTO.setStatus(savedProduct.isStatus());
        productDTO.setProductCategoryId(savedProduct.getProductCategory().getProductCategoryId());
        productDTO.setUserId(savedProduct.getUser().getUserId());

        return productDTO;
    }

    @Override
    public void updateStatusProduct(int pId) {
        Product product = productRepository.findById(pId)
                .orElseThrow(() -> new RuntimeException("Product not Found with Id: " + pId));
        product.setStatus(!product.isStatus());
        product.setUpdatedTime(new Date());
        productRepository.save(product);
    }

    @Override
    public ProductDTO getProductDetail(int pId) {
        Product product = productRepository.findById(pId)
                .orElseThrow(() -> new RuntimeException("Product not Found with Id: " + pId));

        return mapper.map(product, ProductDTO.class);
    }

    @Override
    public List<ProductDTO> getProductByCategory(int cId) {
        List<Product> products = productRepository.findProductByProductCategory(cId);

        List<ProductDTO> productDTOS = products.stream()
                .map(product -> {
                    ProductDTO productDTO = mapper.map(product, ProductDTO.class);

                    if (product.getProductCategory() != null) {
                        productDTO.setProductCategoryId(product.getProductCategory().getProductCategoryId());
                    }
                    if (product.getUser() != null) {
                        productDTO.setUserId(product.getUser().getUserId());
                    }

                    return productDTO;
                })
                .collect(Collectors.toList());

        return productDTOS.isEmpty() ? null : productDTOS;
    }


}
