package com.product.globie.repository;

import com.product.globie.entity.UserMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserMemberRepository extends JpaRepository<UserMember, Integer> {
    @Query("select um from UserMember um where um.order.orderId = :id")
    Optional<UserMember> findByOrder(@Param("id") int id);

    @Query("select um from UserMember um where um.user.userId = :id")
    Optional<UserMember> findByUserID(@Param("id") int id);

    Optional<UserMember> findByStorePhone(String storePhone);

    Optional<UserMember> findByStoreName(String storeName);

    Optional<UserMember> findByStoreAddress(String storeAddress);

    @Query("select um from UserMember um where um.user.userId = :id and um.status = true")
    UserMember findByUserIdAndStatusTrue(@Param("id") int id);

    @Query("select um from UserMember um where um.user.userId = :id and um.status = false")
    UserMember findByUserIdAndStatusFalse(@Param("id") int id);
}
