package com.product.globie.service;

import com.product.globie.entity.User;
import com.product.globie.payload.DTO.AccountDTO;
import com.product.globie.payload.request.CreateAccountRequest;

import java.util.List;

public interface AccountService {
    AccountDTO createAccount(CreateAccountRequest user);
    User updateAccount(User user);
    void deleteAccount(int id);
    AccountDTO getAccount(String username);
    List<AccountDTO> getAllAccount();
}
