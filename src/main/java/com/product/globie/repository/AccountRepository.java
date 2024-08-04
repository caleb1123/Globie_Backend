package com.product.globie.repository;

import com.product.globie.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
    Optional<Account> findByUserName(String userName);
    Optional<Account> findByEmail(String email);
    Optional<Account> findByPhone(String phone);
}
