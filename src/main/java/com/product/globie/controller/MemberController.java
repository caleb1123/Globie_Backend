package com.product.globie.controller;

import com.product.globie.payload.DTO.CommentDTO;
import com.product.globie.payload.DTO.MemberDTO;
import com.product.globie.payload.request.CommentRequest;
import com.product.globie.payload.request.CreateMemberRequest;
import com.product.globie.payload.request.UpdateCommentRequest;
import com.product.globie.payload.response.ApiResponse;
import com.product.globie.service.MemberService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.version}/member")
@Slf4j
@CrossOrigin(origins = "https://globie-front-cgxbtuyd8-dolakiens-projects.vercel.app")

public class MemberController {
    @Autowired
    MemberService memberService;



    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<MemberDTO>>> getAllMemberLevel() {
        List<MemberDTO> memberDTOS = memberService.getAllMemberLevel();
        ApiResponse<List<MemberDTO>> response = ApiResponse.<List<MemberDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched Member Level")
                .data(memberDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create_member_level")
    public ResponseEntity<ApiResponse<String>> createMemberLevel(@RequestBody CreateMemberRequest createMemberRequest) {
        memberService.createMemberLevel(createMemberRequest);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(HttpStatus.CREATED.value())
                .message("Successfully created Member Level")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete_member_level/{id}")
    public ResponseEntity<ApiResponse<String>> deleteMemberLevel(@PathVariable int id) {
        memberService.deleteMemberLevel(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully deleted Member Level")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/delete_store/{userMemberId}")
    public ResponseEntity<ApiResponse<String>> deleteStore(@PathVariable int userMemberId) throws MessagingException {
        memberService.deleteStorePackage(userMemberId);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully updated Store ")
                .build();
        return ResponseEntity.ok(response);
    }
}
