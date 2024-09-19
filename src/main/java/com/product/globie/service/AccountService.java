package com.product.globie.service;

import com.product.globie.entity.User;
import com.product.globie.payload.DTO.AccountDTO;

import java.util.List;

public interface AccountService {
    User createAccount(User user);
    User updateAccount(User user);
    void deleteAccount(int id);
    AccountDTO getAccount(String username);
    List<AccountDTO> getAllAccount();
}
