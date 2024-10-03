package com.product.globie.service.impl;

import com.product.globie.config.Util;
import com.product.globie.entity.Product;
import com.product.globie.entity.Rate;
import com.product.globie.entity.User;
import com.product.globie.exception.AppException;
import com.product.globie.exception.ErrorCode;
import com.product.globie.payload.DTO.RateDTO;
import com.product.globie.payload.request.CreateRateRequest;
import com.product.globie.payload.request.UpdateRateRequest;
import com.product.globie.repository.ProductRepository;
import com.product.globie.repository.RateRepository;
import com.product.globie.repository.UserRepository;
import com.product.globie.service.RateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class RateServiceImpl implements RateService {
    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Util util;
    @Override
    public CreateRateRequest createRate(CreateRateRequest rateDTO) {
        Rate rate =  rateRepository.getReferenceById(rateDTO.getId());
        Product product = productRepository.findById(rateDTO.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_FOUND));
        if(rate == null){
            throw  new AppException(ErrorCode.ITEM_NOT_FOUND);
        }
        if(rateDTO.getRating() < 1 || rateDTO.getRating() > 5){
            throw new AppException(ErrorCode.INVALID_INPUT);
        }
        Rate createdRate = Rate.builder()
                .rating(rateDTO.getRating())
                .description(rateDTO.getDescription())
                .product(product)
                .user(util.getUserFromAuthentication())
                .createdTime(String.valueOf(LocalDate.now()))
                .build();
        rateRepository.save(createdRate);
        rateDTO.setId(createdRate.getId());
        return rateDTO;
    }

    @Override
    public RateDTO updateRate(UpdateRateRequest rateDTO) {
        Rate rate = rateRepository.getReferenceById(rateDTO.getId());
        if(rate == null){
            throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        }
        rate.setRating(rateDTO.getRating());
        rate.setDescription(rateDTO.getDescription());
        rate.setUpdatedTime(String.valueOf(LocalDate.now()));
        rate = rateRepository.save(rate);
        return modelMapper.map(rate, RateDTO.class);
    }

    @Override
    public RateDTO getRateById(int id) {
        Rate rate = rateRepository.getReferenceById(id);
        if(rate == null){
            throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        }
        return modelMapper.map(rate, RateDTO.class);
    }

    @Override
    public void deleteRate(int id) {
        Rate rate = rateRepository.getReferenceById(id);
        if(rate == null){
            throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        }
        rateRepository.delete(rate);
    }

    @Override
    public List<RateDTO> getAllRates() {
        List<Rate> rates = rateRepository.findAll();
        if(rates.isEmpty()){
            throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        }
        return rates.stream().map(rate -> modelMapper.map(rate, RateDTO.class)).toList();
    }

    @Override
    public List<RateDTO> getRatesByProductId(int productId) {
        List<Rate> rates = rateRepository.findRatesByProduct_ProductId(productId);
        if(rates.isEmpty()){
            throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        }
        return rates.stream().map(rate -> modelMapper.map(rate, RateDTO.class)).toList();
    }

    @Override
    public List<RateDTO> getRatesByUserId(int userId) {
        List<Rate> rates = rateRepository.findRatesByUser_UserId(userId);
        if(rates.isEmpty()){
            throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        }
        return rates.stream().map(rate -> modelMapper.map(rate, RateDTO.class)).toList();
    }
}
