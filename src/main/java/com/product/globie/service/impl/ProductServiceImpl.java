package com.product.globie.service.impl;

import com.product.globie.config.Util;
import com.product.globie.entity.*;

import com.product.globie.entity.Enum.EProductStatus;
import com.product.globie.entity.Enum.ERole;
import com.product.globie.payload.DTO.AccountDTO;
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
                        productDTO.setUser(mapper.map(product.getUser(), AccountDTO.class));
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
                        productDTO.setUser(mapper.map(product.getUser(), AccountDTO.class));
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
                        productDTO.setUser(mapper.map(product.getUser(), AccountDTO.class));
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
                        productDTO.setUser(mapper.map(product.getUser(), AccountDTO.class));
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
                .filter(product -> product.getStatus().equals("Selling"))
                .sorted((p1, p2) -> {
                    boolean isStorekeeper1 = p1.getUser() != null && p1.getUser().getRole().getRoleName().name().equals("STOREKEEPER");
                    boolean isStorekeeper2 = p2.getUser() != null && p2.getUser().getRole().getRoleName().name().equals("STOREKEEPER");

                    // Products of STOREKEEPER users should come first
                    if (isStorekeeper1 && !isStorekeeper2) {
                        return -1;
                    } else if (!isStorekeeper1 && isStorekeeper2) {
                        return 1;
                    } else {
                        return 0; // If both are the same, no change in order
                    }
                })
                .map(product -> {
                    ProductDTO productDTO = mapper.map(product, ProductDTO.class);

                    if (product.getProductCategory() != null) {
                        productDTO.setProductCategory(mapper.map(product.getProductCategory(), ProductCategoryDTO.class));
                    }
                    if (product.getUser() != null) {
                        productDTO.setUser(mapper.map(product.getUser(), AccountDTO.class));
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
                        productDTO.setUser(mapper.map(product.getUser(), AccountDTO.class));
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
                        productDTO.setUser(mapper.map(product.getUser(), AccountDTO.class));
                    }

                    return productDTO;
                })
                .collect(Collectors.toList());

        return productDTOS.isEmpty() ? null : productDTOS;
    }

    @Override
    public List<ProductDTO> getAllProductStatusSellingStore() {
        List<Product> products = productRepository.findAll();

        List<ProductDTO> productDTOS = products.stream()
                .filter(product -> product.getStatus().equals("Selling"))
                .filter(product -> product.getUser() != null &&
                        product.getUser().getRole().getRoleName().name().equals("STOREKEEPER"))
                .map(product -> {
                    ProductDTO productDTO = mapper.map(product, ProductDTO.class);

                    if (product.getProductCategory() != null) {
                        productDTO.setProductCategory(mapper.map(product.getProductCategory(), ProductCategoryDTO.class));
                    }
                    if (product.getUser() != null) {
                        productDTO.setUser(mapper.map(product.getUser(), AccountDTO.class));
                    }

                    return productDTO;
                })
                .collect(Collectors.toList());

        return productDTOS.isEmpty() ? null : productDTOS;
    }

    @Override
    public List<ProductDTO> getAllProductStatusSellingUser() {
        List<Product> products = productRepository.findAll();

        List<ProductDTO> productDTOS = products.stream()
                .filter(product -> product.getStatus().equals("Selling"))
                .filter(product -> product.getUser() != null &&
                        product.getUser().getRole().getRoleName().name().equals("USER"))
                .map(product -> {
                    ProductDTO productDTO = mapper.map(product, ProductDTO.class);

                    if (product.getProductCategory() != null) {
                        productDTO.setProductCategory(mapper.map(product.getProductCategory(), ProductCategoryDTO.class));
                    }
                    if (product.getUser() != null) {
                        productDTO.setUser(mapper.map(product.getUser(), AccountDTO.class));
                    }

                    return productDTO;
                })
                .collect(Collectors.toList());

        return productDTOS.isEmpty() ? null : productDTOS;    }


    @Override
    public ProductDTO createProduct(CreateProductRequest productRequest) {
        int quantityProductUser = productRepository.countProductOfUser(util.getUserFromAuthentication().getUserId());
        if(util.getUserFromAuthentication().getRole().getRoleName().name().equals(ERole.USER.name())) {
            if (quantityProductUser > 5) {
                throw new RuntimeException("You posted Product more than 5 products!");
            }
        }

        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getDescription());
        product.setBrand(productRequest.getBrand());
        product.setOrigin(productRequest.getOrigin());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setWarranty(productRequest.getWarranty());
        product.setCreatedTime(new Date());
        if(util.getUserFromAuthentication().getRole().getRoleName().name().equals(ERole.STOREKEEPER.name())){
            product.setStatus(EProductStatus.Selling.name());
        }else {
            product.setStatus(EProductStatus.Processing.name());
        }
        product.setUser(util.getUserFromAuthentication());

        ProductCategory productCategory = productCategoryRepository
                .findById(productRequest.getProductCategoryId())
                .orElseThrow(() -> new RuntimeException("ProductCategory not found with Id: " + productRequest.getProductCategoryId()));
        product.setProductCategory(productCategory);

        Product savedProduct = productRepository.save(product);

        ProductDTO productDTO = mapper.map(savedProduct, ProductDTO.class);
        productDTO.setProductCategory(mapper.map(savedProduct.getProductCategory(), ProductCategoryDTO.class));
        productDTO.setUser(mapper.map(savedProduct.getUser(), AccountDTO.class));

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
        product.setPrice(product.getPrice());
        if(product.getQuantity() == 0 && productRequest.getQuantity() > 0){
                product.setQuantity(productRequest.getQuantity());
                product.setStatus(EProductStatus.Selling.name());
        }else{
            product.setQuantity(productRequest.getQuantity());
        }
        product.setWarranty(productRequest.getWarranty());
        product.setUpdatedTime(new Date());

        Product savedProduct = productRepository.save(product);

        ProductDTO productDTO = mapper.map(savedProduct, ProductDTO.class);
        productDTO.setProductCategory(mapper.map(savedProduct.getProductCategory(), ProductCategoryDTO.class));
        productDTO.setUser(mapper.map(savedProduct.getUser(), AccountDTO.class));

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
        productDTO.setUser(mapper.map(product.getUser(), AccountDTO.class));

        return productDTO;
    }

    public List<ProductDTO> filterProducts(Integer cId, String brand, String origin, Double minPrice, Double maxPrice) {
        List<Product> products = productRepository.filterProducts(
                cId != null ? cId : null,
                brand,
                origin,
                minPrice,
                maxPrice
        );

        if (products.isEmpty()) {
            return Collections.emptyList();
        }

        List<ProductDTO> productDTOS = products.stream()
                .filter(product -> product.getStatus().equals("Selling"))
                .map(product -> {
                    ProductDTO productDTO = mapper.map(product, ProductDTO.class);

                    if (product.getProductCategory() != null) {
                        productDTO.setProductCategory(mapper.map(product.getProductCategory(), ProductCategoryDTO.class));
                    }
                    if (product.getUser() != null) {
                        productDTO.setUser(mapper.map(product.getUser(), AccountDTO.class));
                    }

                    return productDTO;
                })
                .collect(Collectors.toList());

        return productDTOS.isEmpty() ? null : productDTOS;
    }


    @Override
    public List<ProductDTO> getProductByCategory(int cId) {
        List<Product> products = productRepository.findProductByProductCategory(cId);
        if (products.isEmpty()) {
            return Collections.emptyList();
        }
        List<ProductDTO> productDTOS = products.stream()
                .filter(product -> product.getStatus().equals("Selling"))
                .map(product -> {
                    ProductDTO productDTO = mapper.map(product, ProductDTO.class);

                    if (product.getProductCategory() != null) {
                        productDTO.setProductCategory(mapper.map(product.getProductCategory(), ProductCategoryDTO.class));
                    }
                    if (product.getUser() != null) {
                        productDTO.setUser(mapper.map(product.getUser(), AccountDTO.class));
                    }

                    return productDTO;
                })
                .collect(Collectors.toList());

        return productDTOS; // No need for the additional check here
    }

    @Override
    public List<ProductDTO> getProductByCategoryOfUser(int cId) {
        List<Product> products = productRepository.findProductByProductCategory(cId);
        if (products.isEmpty()) {
            return Collections.emptyList();
        }
        List<ProductDTO> productDTOS = products.stream()
                .filter(product -> product.getStatus().equals("Selling"))
                .filter(product -> product.getUser() != null &&
                        product.getUser().getRole().getRoleName().name().equals("USER"))
                .map(product -> {
                    ProductDTO productDTO = mapper.map(product, ProductDTO.class);

                    if (product.getProductCategory() != null) {
                        productDTO.setProductCategory(mapper.map(product.getProductCategory(), ProductCategoryDTO.class));
                    }
                    if (product.getUser() != null) {
                        productDTO.setUser(mapper.map(product.getUser(), AccountDTO.class));
                    }

                    return productDTO;
                })
                .collect(Collectors.toList());

        return productDTOS;
    }

    @Override
    public List<ProductDTO> getProductByCategoryOfStore(int cId) {
        List<Product> products = productRepository.findProductByProductCategory(cId);
        if (products.isEmpty()) {
            return Collections.emptyList();
        }
        List<ProductDTO> productDTOS = products.stream()
                .filter(product -> product.getStatus().equals("Selling"))
                .filter(product -> product.getUser() != null &&
                        product.getUser().getRole().getRoleName().name().equals("STOREKEEPER"))
                .map(product -> {
                    ProductDTO productDTO = mapper.map(product, ProductDTO.class);

                    if (product.getProductCategory() != null) {
                        productDTO.setProductCategory(mapper.map(product.getProductCategory(), ProductCategoryDTO.class));
                    }
                    if (product.getUser() != null) {
                        productDTO.setUser(mapper.map(product.getUser(), AccountDTO.class));
                    }

                    return productDTO;
                })
                .collect(Collectors.toList());

        return productDTOS;
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

    @Override
    public Integer countProductSelling() {
        Integer product = productRepository.countProductSelling(); // Dùng Integer để xử lý trường hợp null
        if (product == null) {
            return 0; // Trả về 0 nếu kết quả là null
        }
        return product; // Trả về giá trị nếu khác null
    }

    @Override
    public Integer countProductSold() {
        Integer product = productRepository.countProductSold(); // Dùng Integer để xử lý trường hợp null
        if (product == null) {
            return 0; // Trả về 0 nếu kết quả là null
        }
        return product; // Trả về giá trị nếu khác null
    }

    @Override
    public Integer countProductProcessing() {
            Integer product = productRepository.countProductProcessing(); // Dùng Integer để xử lý trường hợp null
            if (product == null) {
                return 0; // Trả về 0 nếu kết quả là null
            }
            return product; // Trả về giá trị nếu khác null
    }

    @Override
    public List<ProductDTO> searchProductsByNameOrBrand(String searchKeyword) {
        List<Product> products = productRepository.searchProductsByNameOrBrand(searchKeyword);
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
                        productDTO.setUser(mapper.map(product.getUser(), AccountDTO.class));
                    }

                    return productDTO;
                })
                .collect(Collectors.toList());

        return productDTOS;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
