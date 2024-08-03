package com.product.globie.mapper;

import com.product.globie.entity.Account;
import com.product.globie.payload.DTO.AccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDTO toDTO(Account user);
    Account toEntity(AccountDTO dto);
}
