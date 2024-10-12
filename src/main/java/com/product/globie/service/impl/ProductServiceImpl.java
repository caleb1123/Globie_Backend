package com.product.globie.service.impl;

import com.product.globie.config.Util;
import com.product.globie.entity.*;

import com.product.globie.entity.Enum.EProductStatus;
import com.product.globie.entity.Enum.ERole;
import com.product.globie.payload.DTO.ProductCategoryDTO;
import com.product.globie.payload.DTO.ProductDTO;
import com.product.globie.payload.DTO.ProductImageDTO;
import com.product.globie.payload.request.CreateProductRequest;
import com.product.globie.payload.request.UpdateProductRequest;
import com.product.globie.repository.ProductCategoryRepository;
import com.product.globie.repository.ProductImageRepository;
import com.product.globie.repository.ProductRepository;
import com.product.globie.repository.UserRepository;
import com.product.globie.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
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

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    ProductImageRepository productImageRepository;



    @Override
    public List<ProductDTO> getAllProduct() {
        List<Product> products = productRepository.findAll();

        List<ProductDTO> productDTOS = products.stream()
                .map(product -> {
                    ProductDTO productDTO = mapper.map(product, ProductDTO.class);

                    if (product.getProductCategory() != null) {
                        productDTO.setProductCategory(mapper.map(product.getProductCategory(), ProductCategoryDTO.class));
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
    public List<ProductDTO> getProductByUserStatusProcessing() {
        int uId = util.getUserFromAuthentication().getUserId();
        List<Product> products = productRepository.findProductByUser(uId);
        if(products.isEmpty()){
            throw new RuntimeException("There are no products of this User Id: " + uId);
        }
        List<ProductDTO> productDTOS = products.stream()
                .filter(Product -> Product.getStatus().equals("Processing"))
                .map(product -> {
                    ProductDTO productDTO = mapper.map(product, ProductDTO.class);

                    if (product.getProductCategory() != null) {
                        productDTO.setProductCategory(mapper.map(product.getProductCategory(), ProductCategoryDTO.class));
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
    public List<ProductDTO> getProductByUserStatusSelling() {
        int uId = util.getUserFromAuthentication().getUserId();
        List<Product> products = productRepository.findProductByUser(uId);
        if(products.isEmpty()){
            throw new RuntimeException("There are no products of this User Id: " + uId);
        }
        List<ProductDTO> productDTOS = products.stream()
                .filter(Product -> Product.getStatus().equals("Selling"))
                .map(product -> {
                    ProductDTO productDTO = mapper.map(product, ProductDTO.class);

                    if (product.getProductCategory() != null) {
                        productDTO.setProductCategory(mapper.map(product.getProductCategory(), ProductCategoryDTO.class));
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
    public List<ProductDTO> getProductByUserStatusSold() {
        int uId = util.getUserFromAuthentication().getUserId();
        List<Product> products = productRepository.findProductByUser(uId);
        if(products.isEmpty()){
            throw new RuntimeException("There are no products of this User Id: " + uId);
        }
        List<ProductDTO> productDTOS = products.stream()
                .filter(Product -> Product.getStatus().equals("Sold"))
                .map(product -> {
                    ProductDTO productDTO = mapper.map(product, ProductDTO.class);

                    if (product.getProductCategory() != null) {
                        productDTO.setProductCategory(mapper.map(product.getProductCategory(), ProductCategoryDTO.class));
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
    public List<ProductDTO> getAllProductStatusSelling() {
        List<Product> products = productRepository.findAll();

        List<ProductDTO> productDTOS = products.stream()
                .filter(Product -> Product.getStatus().equals("Selling"))
                .map(product -> {
                    ProductDTO productDTO = mapper.map(product, ProductDTO.class);

                    if (product.getProductCategory() != null) {
                        productDTO.setProductCategory(mapper.map(product.getProductCategory(), ProductCategoryDTO.class));
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
    public List<ProductDTO> getAllProductStatusSold() {
        if(!util.getUserFromAuthentication().getRole().getRoleName().equals(ERole.STAFF)){
            throw new RuntimeException("USER NOT ALLOWED!");
        }

        List<Product> products = productRepository.findAll();

        List<ProductDTO> productDTOS = products.stream()
                .filter(Product -> Product.getStatus().equals("Sold"))
                .map(product -> {
                    ProductDTO productDTO = mapper.map(product, ProductDTO.class);

                    if (product.getProductCategory() != null) {
                        productDTO.setProductCategory(mapper.map(product.getProductCategory(), ProductCategoryDTO.class));
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
    public List<ProductDTO> getAllProductStatusProcessing() {
        if(!util.getUserFromAuthentication().getRole().getRoleName().equals(ERole.STAFF)){
            throw new RuntimeException("USER NOT ALLOWED!");
        }

        List<Product> products = productRepository.findAll();

        List<ProductDTO> productDTOS = products.stream()
                .filter(Product -> Product.getStatus().equals("Processing"))
                .map(product -> {
                    ProductDTO productDTO = mapper.map(product, ProductDTO.class);

                    if (product.getProductCategory() != null) {
                        productDTO.setProductCategory(mapper.map(product.getProductCategory(), ProductCategoryDTO.class));
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
    public ProductDTO createProduct(CreateProductRequest productRequest) {
        Product product = new Product();

        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getDescription());
        product.setBrand(productRequest.getBrand());
        product.setOrigin(productRequest.getOrigin());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setWarranty(productRequest.getWarranty());
        product.setCreatedTime(new Date());
        product.setStatus(EProductStatus.Processing.name());
        product.setUser(util.getUserFromAuthentication());

        ProductCategory productCategory = productCategoryRepository
                .findById(productRequest.getProductCategoryId())
                .orElseThrow(() -> new RuntimeException("ProductCategory not found with Id: " + productRequest.getProductCategoryId()));
        product.setProductCategory(productCategory);

        Product savedProduct = productRepository.save(product);

        ProductDTO productDTO = mapper.map(savedProduct, ProductDTO.class);
        productDTO.setProductCategory(mapper.map(savedProduct.getProductCategory(), ProductCategoryDTO.class));
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
    public ProductDTO updateProduct(UpdateProductRequest productRequest, int pId) {

        Product product = productRepository.findById(pId)
                .orElseThrow(() -> new RuntimeException("Product not Found with Id: " + pId));
        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getDescription());
        product.setBrand(productRequest.getBrand());
        product.setOrigin(productRequest.getOrigin());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setWarranty(productRequest.getWarranty());
        product.setUpdatedTime(new Date());

        Product savedProduct = productRepository.save(product);

        ProductDTO productDTO = mapper.map(savedProduct, ProductDTO.class);
        productDTO.setProductCategory(mapper.map(savedProduct.getProductCategory(), ProductCategoryDTO.class));
        productDTO.setUserId(savedProduct.getUser().getUserId());

        return productDTO;
    }

    @Override
    public void updateStatusProduct(int pId) {
        Product product = productRepository.findById(pId)
                .orElseThrow(() -> new RuntimeException("Product not Found with Id: " + pId));

        product.setStatus(EProductStatus.Selling.name());
        product.setUpdatedTime(new Date());
        productRepository.save(product);
    }

    @Override
    public ProductDTO getProductDetail(int pId) {
        Product product = productRepository.findById(pId)
                .orElseThrow(() -> new RuntimeException("Product not Found with Id: " + pId));
        ProductDTO productDTO = mapper.map(product, ProductDTO.class);
        productDTO.setProductCategory(mapper.map(product.getProductCategory(), ProductCategoryDTO.class));
        productDTO.setUserId(product.getUser().getUserId());

        return productDTO;
    }

    @Override
    public List<ProductDTO> getProductByCategory(int cId) {
        List<Product> products = productRepository.findProductByProductCategory(cId);
        if (products.isEmpty()) {
            return Collections.emptyList();
        }
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> {
                    ProductDTO productDTO = mapper.map(product, ProductDTO.class);

                    if (product.getProductCategory() != null) {
                        productDTO.setProductCategory(mapper.map(product.getProductCategory(), ProductCategoryDTO.class));
                    }
                    if (product.getUser() != null) {
                        productDTO.setUserId(product.getUser().getUserId());
                    }

                    return productDTO;
                })
                .collect(Collectors.toList());

        return productDTOS; // No need for the additional check here
    }

    @Override
    public List<ProductImageDTO> uploadMultipleProductImages(MultipartFile[] multipartFiles, int productId) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with Id: " + productId));

        Integer imageCount = productImageRepository.countImageByProduct(productId);
        if(imageCount == null) imageCount = 0;
        if(imageCount >= 10) throw new RuntimeException("Maximum of 10 images already uploaded for this product.");

        List<ProductImageDTO> uploadedImages = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            File file = convertMultiPartToFile(multipartFile);
            Map uploadResult = cloudinaryService.uploadFile(file);

            ProductImage productImage = new ProductImage();
            productImage.setProduct(product);
            productImage.setImagePath(uploadResult.get("url").toString());
            productImage.setProductImageCode(uploadResult.get("public_id").toString());
            productImage.setStatus(true);

            ProductImage savedProductImage = productImageRepository.save(productImage);

            ProductImageDTO productImageDTO = mapper.map(savedProductImage, ProductImageDTO.class);
            productImageDTO.setProductId(savedProductImage.getProduct().getProductId());

            uploadedImages.add(productImageDTO);
        }

        return uploadedImages;
    }


    @Override
    public List<ProductImageDTO> getAllImageByProduct(int productId) {
        List<ProductImage> productImages = productImageRepository.getProductImageByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Image not found with Product Id: " + productId));

        List<ProductImageDTO> productImageDTOS = productImages.stream()
                .map(image -> {
                    ProductImageDTO productImageDTO = mapper.map(image, ProductImageDTO.class);

                    if (image.getProduct() != null) {
                        productImageDTO.setProductId(image.getProduct().getProductId());
                    }

                    return productImageDTO;
                })
                .collect(Collectors.toList());

        return productImageDTOS.isEmpty() ? null : productImageDTOS;
    }

    @Override
    public List<ProductImageDTO> getAllImageByProductStatusTrue(int productId) {
        List<ProductImage> productImages = productImageRepository.getProductImageByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Image not found with Product Id: " + productId));

        List<ProductImageDTO> productImageDTOS = productImages.stream()
                .filter(ProductImage :: isStatus)
                .map(image -> {
                    ProductImageDTO productImageDTO = mapper.map(image, ProductImageDTO.class);

                    if (image.getProduct() != null) {
                        productImageDTO.setProductId(image.getProduct().getProductId());
                    }

                    return productImageDTO;
                })
                .collect(Collectors.toList());

        return productImageDTOS.isEmpty() ? null : productImageDTOS;
    }

    @Override
    public void deleteProductImage(String imageCode) throws IOException {
        ProductImage productImage = productImageRepository.getProductImageByImageCode(imageCode)
                .orElseThrow(() -> new RuntimeException("Image not found with Product Image Code: " + imageCode));

        String publicId = extractPublicId(productImage.getImagePath());

        Map result = cloudinaryService.deleteFile(publicId);
        if ("ok".equals(result.get("result"))) {
            productImage.setStatus(false);
            productImageRepository.save(productImage);
        } else {
            throw new RuntimeException("Failed to delete image from Cloudinary: " + result);
        }
    }

    private String extractPublicId(String imageUrl) {
        // Kiểm tra định dạng URL
        if (imageUrl == null || imageUrl.isEmpty()) {
            throw new IllegalArgumentException("Image URL cannot be null or empty");
        }

        // Ví dụ: https://res.cloudinary.com/demo/image/upload/v1234567890/public_id.jpg
        // Trích xuất public_id "v1234567890/public_id"
        int startIndex = imageUrl.lastIndexOf("/") + 1; // Vị trí bắt đầu của public_id
        int endIndex = imageUrl.lastIndexOf("."); // Vị trí kết thúc trước đuôi file
        if (startIndex < 0 || endIndex < 0 || startIndex >= endIndex) {
            throw new RuntimeException("Invalid image URL format: " + imageUrl);
        }
        return imageUrl.substring(startIndex, endIndex); // Trả về public_id
    }

    @Override
    public ProductImageDTO getProductImageByCode(String imageCode) {
        ProductImage productImage = productImageRepository.getProductImageByImageCode(imageCode)
                .orElseThrow(() -> new RuntimeException("Image not found with Product Image Code: " + imageCode));

        ProductImageDTO productImageDTO = mapper.map(productImage, ProductImageDTO.class);
        productImageDTO.setProductId(productImage.getProduct().getProductId());

        return productImageDTO;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
