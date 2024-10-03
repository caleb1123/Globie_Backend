package com.product.globie.config;

import com.product.globie.entity.Rate;
import com.product.globie.entity.User;
import com.product.globie.payload.DTO.AccountDTO;
import com.product.globie.payload.DTO.RateDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        // Tạo object và cấu hình
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.typeMap(User.class, AccountDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getRole().getRoleId(), AccountDTO::setRoleId);
        });

        modelMapper.typeMap(Rate.class, RateDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getUser().getUserId(), RateDTO::setUserId);
            mapper.map(src -> src.getProduct().getProductId(), RateDTO::setProductId);
        });
        return modelMapper;
    }
}
