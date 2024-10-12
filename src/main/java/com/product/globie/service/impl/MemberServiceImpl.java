package com.product.globie.service.impl;

import com.product.globie.config.Util;
import com.product.globie.entity.*;
import com.product.globie.entity.Enum.EOrderStatus;
import com.product.globie.entity.Enum.EProductStatus;
import com.product.globie.payload.DTO.*;
import com.product.globie.payload.request.CreateMemberRequest;
import com.product.globie.payload.request.CreateOrderRequest;
import com.product.globie.repository.*;
import com.product.globie.service.MemberService;
import jakarta.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    UserMemberRepository userMemberRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Util util;

    @Autowired
    MailService mailService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    OrderRepository orderRepository;



    @Override
    public List<MemberDTO> getAllMemberLevel() {
        List<MemberLevel> memberLevels = memberRepository.findAll();

        return memberLevels.stream()
                .map(memberLevel -> mapper.map(memberLevel, MemberDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void createMemberLevel(CreateMemberRequest createMemberRequest) {
        MemberLevel memberLevel = new MemberLevel();
        memberLevel.setLevelName(createMemberRequest.getLevelName());
        memberLevel.setDescription(createMemberRequest.getDescription());
        memberLevel.setPrice(createMemberRequest.getPrice());
        memberLevel.setDurationInMonths(createMemberRequest.getDurationInMonths());

        memberRepository.save(memberLevel);
    }

    @Override
    public void deleteMemberLevel(int id) {
        MemberLevel memberLevel = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member Level not found!"));

        memberRepository.delete(memberLevel);
    }

    @Override
    public void deleteStorePackage(int id) throws MessagingException {
        UserMember userMember = userMemberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User member not found!"));

        userMember.setStatus(false);

        MemberLevel memberLevel = memberRepository.findById(userMember.getMemberLevel().getMemberLevelId())
                .orElseThrow(() -> new RuntimeException("Member Level not found!"));
        User user = userRepository.findById(userMember.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found!"));
        Role role = roleRepository.findById(4)
                .orElseThrow(() -> new RuntimeException("Role not found!"));
        user.setRole(role);

        mailService.SendEmailStoreMember(user.getEmail(), user.getFullName(), user.getUserName(), userMember.getMemberStartDate(), userMember.getMemberEndDate(), memberLevel.getLevelName());
        userRepository.save(user);
        userMemberRepository.save(userMember);
    }

    @Scheduled(cron = "0 12 22 * * ?")
    public void updateExpiredMemberStatus() throws MessagingException {
        List<UserMember> userMembers = userMemberRepository.findAll();

        for (UserMember userMember : userMembers) {
            LocalDate endDate = userMember.getMemberEndDate();
            LocalDate today = LocalDate.now();

            if (endDate != null && today.isAfter(endDate) && userMember.isStatus()) {
                MemberLevel memberLevel = memberRepository.findById(userMember.getMemberLevel().getMemberLevelId())
                        .orElseThrow(() -> new RuntimeException("Member Level not found!"));
                User user = userRepository.findById(userMember.getUser().getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found!"));
                Role role = roleRepository.findById(4)
                        .orElseThrow(() -> new RuntimeException("Role not found!"));

                user.setRole(role);
                userMember.setStatus(false);

                mailService.SendEmailStoreMemberExpired(user.getEmail(), user.getFullName(), user.getUserName(), userMember.getMemberStartDate(), userMember.getMemberEndDate(), userMember.getMemberEndDate().plusDays(1), memberLevel.getLevelName());
                userRepository.save(user);
                userMemberRepository.save(userMember);
            }
        }
    }
}
