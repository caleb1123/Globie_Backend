package com.product.globie.service;

import com.product.globie.entity.User;
import com.product.globie.payload.DTO.AccountDTO;
import com.product.globie.payload.request.CreateAccountRequest;
import com.product.globie.payload.response.MyAccountResponse;

import java.util.List;

public interface AccountService {
    AccountDTO createAccount(CreateAccountRequest user);
    User updateAccount(User user);
    void deleteAccount(String username);
    AccountDTO getAccount(String username);
    List<AccountDTO> getAllAccount();

    MyAccountResponse myAccount();
}
