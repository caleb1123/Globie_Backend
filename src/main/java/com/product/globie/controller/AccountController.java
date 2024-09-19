package com.product.globie.controller;

import com.product.globie.payload.DTO.AccountDTO;
import com.product.globie.payload.request.CreateAccountRequest;
import com.product.globie.payload.response.ApiResponse;
import com.product.globie.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.version}/account")
@Slf4j
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<AccountDTO>>> getAllAccount() {
        List<AccountDTO> users = accountService.getAllAccount();
        ApiResponse<List<AccountDTO>> response = ApiResponse.<List<AccountDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched all accounts")
                .data(users)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{username}")
    public ResponseEntity<ApiResponse<AccountDTO>> getAccount(@PathVariable String username) {
        AccountDTO user = accountService.getAccount(username);
        ApiResponse<AccountDTO> response = ApiResponse.<AccountDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched account")
                .data(user)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<AccountDTO>> createAccount(@RequestBody CreateAccountRequest accountRequest) {
        AccountDTO user = accountService.createAccount(accountRequest);
        ApiResponse<AccountDTO> response = ApiResponse.<AccountDTO>builder()
                .code(HttpStatus.CREATED.value())
                .message("Account successfully created")
                .data(user)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
