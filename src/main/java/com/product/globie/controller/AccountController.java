package com.product.globie.controller;

import com.product.globie.entity.User;
import com.product.globie.payload.DTO.AccountDTO;
import com.product.globie.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.version}/account")
@Slf4j
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/all")
    public ResponseEntity<List<AccountDTO>> getAllAccount() {
        List<AccountDTO> users = accountService.getAllAccount();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/get/{username}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable String username) {
        AccountDTO user = accountService.getAccount(username);
        return ResponseEntity.ok(user);
    }
}
