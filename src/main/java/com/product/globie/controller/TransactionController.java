package com.product.globie.controller;

import com.product.globie.payload.DTO.ProductDTO;
import com.product.globie.payload.DTO.TransactionDTO;
import com.product.globie.payload.response.ApiResponse;
import com.product.globie.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("${api.version}/transaction")
@Slf4j
@CrossOrigin(origins = "https://globie.vercel.app")

public class TransactionController {
    @Autowired
    TransactionService transactionService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<TransactionDTO>>> getAllTransaction() {
        List<TransactionDTO> transactionDTOS = transactionService.getAllTransaction();
        ApiResponse<List<TransactionDTO>> response = ApiResponse.<List<TransactionDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Transaction")
                .data(transactionDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<TransactionDTO>>> filterTransactions(
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<TransactionDTO> transactionDTOList = transactionService.filterDate(startDate, endDate);
        ApiResponse<List<TransactionDTO>> response = ApiResponse.<List<TransactionDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Transaction")
                .data(transactionDTOList)
                .build();
        return ResponseEntity.ok(response);
    }

}
