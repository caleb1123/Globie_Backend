package com.product.globie.repository;

import com.product.globie.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookMarkRepository extends JpaRepository<Bookmark, Integer> {
    @Query("select b from Bookmark b where b.user.userId = :id")
    List<Bookmark> getBookmarkByUser(@Param("id") int id);


    @Query("select b from Bookmark b where b.user.userId = :uId AND b.product.productId = :pId")
    Optional<Bookmark> getBookmarkByUserAndProduct(@Param("uId") int uId, @Param("pId") int pId);
}
