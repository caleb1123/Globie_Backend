package com.product.globie.service;

import com.product.globie.entity.Transaction;
import com.product.globie.payload.DTO.TransactionDTO;

import java.util.Date;
import java.util.List;

public interface TransactionService {
    List<TransactionDTO> getAllTransaction();
    List<TransactionDTO> filterDate(Date startDate, Date endDate);

}
