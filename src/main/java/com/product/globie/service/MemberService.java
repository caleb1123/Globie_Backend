package com.product.globie.service;

import com.product.globie.payload.DTO.MemberDTO;
import com.product.globie.payload.DTO.OrderDTO;
import com.product.globie.payload.request.CreateMemberRequest;
import com.product.globie.payload.request.CreateOrderRequest;
import jakarta.mail.MessagingException;

import java.util.List;

public interface MemberService {
    List<MemberDTO> getAllMemberLevel();

    void createMemberLevel(CreateMemberRequest createMemberRequest);

    void deleteMemberLevel(int id);

    void deleteStorePackage(int id) throws MessagingException;
}
