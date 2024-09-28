package com.product.globie.service;

import com.product.globie.payload.DTO.MemberDTO;
import com.product.globie.payload.request.CreateMemberRequest;
import jakarta.mail.MessagingException;

import java.util.List;

public interface MemberService {
    List<MemberDTO> getAllMemberLevel();

    void createMemberLevel(CreateMemberRequest createMemberRequest);

    void deleteMemberLevel(int id);

    void createStore(int memberLevelId);

    void updateStatusStore(int id) throws MessagingException;

}
