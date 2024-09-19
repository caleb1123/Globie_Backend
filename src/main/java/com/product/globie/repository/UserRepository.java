package com.product.globie.repository;

import com.product.globie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);

    @Query(value = "SELECT * " +
            "FROM users " +
            "WHERE email = :email " +
            "   OR user_name = :userName " +
            "   OR phone = :phone " +
            "   AND status = 1", nativeQuery = true)
    Optional<User> findAccountByUserNameOrEmailOrPhone(@Param("userName") String userName,
                                                       @Param("email") String email,
                                                       @Param("phone") String phone);

    boolean existsByUserName(String userName);
}
