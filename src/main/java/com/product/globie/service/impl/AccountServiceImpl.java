package com.product.globie.service.impl;

import com.product.globie.entity.User;
import com.product.globie.payload.DTO.AccountDTO;
import com.product.globie.repository.UserRepository;
import com.product.globie.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public User createAccount(User user) {
        return null;
    }

    @Override
    public User updateAccount(User user) {
        return null;
    }

    @Override
    public void deleteAccount(int id) {

    }

    @Override
    public AccountDTO getAccount(String username) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + username));
        return modelMapper.map(user, AccountDTO.class);
    }

    @Override
    public List<AccountDTO> getAllAccount() {
        List<User> users = userRepository.findAll();

        // Ánh xạ từng phần tử trong List<User> sang List<AccountDTO>
        return users.stream()
                .map(user -> modelMapper.map(user, AccountDTO.class))
                .collect(Collectors.toList());
    }
}
