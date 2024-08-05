package com.product.globie.repository;

import com.product.globie.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
    Optional<Account> findByUserName(String userName);
    Optional<Account> findByEmail(String email);
    Optional<Account> findByPhone(String phone);

    @Query(value = "SELECT * " +
            "FROM account " +
            "WHERE email = :email " +
            "   OR user_name = :userName " +
            "   OR phone = :phone " +
            "   AND status = 1", nativeQuery = true)
    Optional<Account> findAccountByUserNameOrEmailOrPhone(@Param("userName") String userName,
                                                          @Param("email") String email,
                                                          @Param("phone") String phone);

}
