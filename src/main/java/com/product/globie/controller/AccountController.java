package com.product.globie.controller;

import com.product.globie.payload.DTO.AccountDTO;
import com.product.globie.payload.DTO.RoleDTO;
import com.product.globie.payload.request.CreateAccountRequest;
import com.product.globie.payload.request.UpdateAccountRequest;
import com.product.globie.payload.response.ApiResponse;
import com.product.globie.payload.response.MyAccountResponse;
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
@CrossOrigin(origins = "https://globie-front-cgxbtuyd8-dolakiens-projects.vercel.app")
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

    @GetMapping("/role/all")
    public ResponseEntity<ApiResponse<List<RoleDTO>>> getAllRole() {
        List<RoleDTO> roleDTOS = accountService.getRoles();
        ApiResponse<List<RoleDTO>> response = ApiResponse.<List<RoleDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched all Roles")
                .data(roleDTOS)
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

    @GetMapping("/my-account")
    public ResponseEntity<ApiResponse<MyAccountResponse>> myAccount() {
        MyAccountResponse user = accountService.myAccount();
        ApiResponse<MyAccountResponse> response = ApiResponse.<MyAccountResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched account")
                .data(user)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<ApiResponse<String>> deleteAccount(@PathVariable String username) {
        accountService.deleteAccount(username);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Account successfully deleted")
                .data("Account successfully deleted")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<ApiResponse<AccountDTO>> updateAccount(@RequestBody UpdateAccountRequest accountDTO, @PathVariable String username) {
        AccountDTO user = accountService.updateAccount(accountDTO, username);
        ApiResponse<AccountDTO> response = ApiResponse.<AccountDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Account successfully updated")
                .data(user)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count_true")
    public ResponseEntity<ApiResponse<Integer>> countUserTrue() {
        int count = accountService.countUserTrue();
        ApiResponse<Integer> response = ApiResponse.<Integer>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched User")
                .data(count)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count_false")
    public ResponseEntity<ApiResponse<Integer>> countUserFalse() {
        int count = accountService.countUserFalse();
        ApiResponse<Integer> response = ApiResponse.<Integer>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched User")
                .data(count)
                .build();
        return ResponseEntity.ok(response);
    }
}
