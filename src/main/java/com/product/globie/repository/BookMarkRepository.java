package com.product.globie.repository;

import com.product.globie.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMarkRepository extends JpaRepository<Bookmark, Integer> {
}
