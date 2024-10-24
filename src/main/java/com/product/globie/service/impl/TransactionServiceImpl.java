package com.product.globie.service.impl;

import com.product.globie.entity.Product;
import com.product.globie.entity.Transaction;
import com.product.globie.payload.DTO.AccountDTO;
import com.product.globie.payload.DTO.ProductCategoryDTO;
import com.product.globie.payload.DTO.ProductDTO;
import com.product.globie.payload.DTO.TransactionDTO;
import com.product.globie.repository.TransactionRepository;
import com.product.globie.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ModelMapper mapper;
    @Override
    public List<TransactionDTO> getAllTransaction() {
        List<Transaction> transactions = transactionRepository.findAll();
        if(transactions.isEmpty()){
            throw new RuntimeException("There are no transactions.");
        }
        List<TransactionDTO> transactionDTOS = transactions.stream()
                .map(transaction -> {
                    TransactionDTO transactionDTO = mapper.map(transaction, TransactionDTO.class);

                    if (transaction.getUser() != null) {
                        transactionDTO.setUserID(transaction.getUser().getUserId());
                    }
                    if (transaction.getOrder() != null) {
                        transactionDTO.setOrderCode(transaction.getOrder().getOrderId());
                    }

                    return transactionDTO;
                })
                .collect(Collectors.toList());

        return transactionDTOS.isEmpty() ? null : transactionDTOS;
    }

    @Override
    public List<TransactionDTO> filterDate(Date startDate, Date endDate) {
        List<Transaction> transactions = transactionRepository.filterTransaction(startDate, endDate);
        if (transactions.isEmpty()) {
            return Collections.emptyList();
        }

        List<TransactionDTO> transactionDTOS = transactions.stream()
                .map(transaction -> {
                    TransactionDTO transactionDTO = mapper.map(transaction, TransactionDTO.class);

                    if (transaction.getOrder() != null) {
                        transactionDTO.setOrderCode(transaction.getOrder().getOrderId());
                    }
                    if (transaction.getUser() != null) {
                        transactionDTO.setUserID(transaction.getUser().getUserId());
                    }

                    return transactionDTO;
                })
                .collect(Collectors.toList());

        return transactionDTOS.isEmpty() ? null : transactionDTOS;
    }


}
