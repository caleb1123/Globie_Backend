package com.product.globie.repository;

import com.product.globie.entity.MemberLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberLevel, Integer> {
}
