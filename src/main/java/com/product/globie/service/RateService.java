package com.product.globie.service;

import com.product.globie.payload.DTO.RateDTO;
import com.product.globie.payload.request.CreateRateRequest;
import com.product.globie.payload.request.UpdateRateRequest;

import java.util.List;

public interface RateService {
    CreateRateRequest createRate(CreateRateRequest rateDTO);

    RateDTO updateRate(UpdateRateRequest rateDTO);

    RateDTO getRateById(int id);

    void deleteRate(int id);

    List<RateDTO> getAllRates();

    List<RateDTO> getRatesByProductId(int productId);

    List<RateDTO> getRatesByUserId(int userId);
}
