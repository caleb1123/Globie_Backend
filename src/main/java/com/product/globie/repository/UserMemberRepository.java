package com.product.globie.repository;

import com.product.globie.entity.UserMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMemberRepository extends JpaRepository<UserMember, Integer> {
}
