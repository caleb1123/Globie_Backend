package com.product.globie.repository;

import com.product.globie.entity.Product;
import com.product.globie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT * from product WHERE user_id = :uId", nativeQuery = true)
    List<Product> findProductByUser(@Param("uId") int uId);

    @Query("SELECT p FROM Product p WHERE p.productCategory.productCategoryId = :cId")
    List<Product> findProductByProductCategory(@Param("cId") int cId);

    @Query("SELECT p FROM Product p WHERE " +
            "(:brand IS NULL OR p.brand = :brand) AND " +
            "(:origin IS NULL OR p.origin = :origin) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<Product> filterProducts(@Param("brand") String brand,
                                 @Param("origin") String origin,
                                 @Param("minPrice") Double minPrice,
                                 @Param("maxPrice") Double maxPrice);

    @Query(value = "SELECT COUNT(*) AS product\n" +
            "FROM product\n" +
            "WHERE status = 'Selling'; ", nativeQuery = true)
    Integer countProductSelling();

    @Query(value = "SELECT COUNT(*) AS product\n" +
            "FROM product\n" +
            "WHERE status = 'Processing'; ", nativeQuery = true)
    Integer countProductProcessing();

    @Query(value = "SELECT COUNT(*) AS product\n" +
            "FROM product\n" +
            "WHERE status = 'Sold'; ", nativeQuery = true)
    Integer countProductSold();
}
