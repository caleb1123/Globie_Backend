package com.product.globie.controller;

import com.product.globie.payload.DTO.PostDTO;
import com.product.globie.payload.DTO.ReportDTO;
import com.product.globie.payload.request.CreatePostRequest;
import com.product.globie.payload.request.CreateReportRequest;
import com.product.globie.payload.request.UpdatePostRequest;
import com.product.globie.payload.response.ApiResponse;
import com.product.globie.service.ReportService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.version}/report")
@Slf4j
@CrossOrigin(origins = "https://globie.vercel.app")

public class ReportController {
    @Autowired
    ReportService reportService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ReportDTO>>> getAllReport() {
        List<ReportDTO> reportDTOS = reportService.getAllReport();
        ApiResponse<List<ReportDTO>> response = ApiResponse.<List<ReportDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched report")
                .data(reportDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/approved")
    public ResponseEntity<ApiResponse<List<ReportDTO>>> getAllReportStatusApproved() {
        List<ReportDTO> reportDTOS = reportService.getAllReportStatusApproved();
        ApiResponse<List<ReportDTO>> response = ApiResponse.<List<ReportDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched report")
                .data(reportDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/rejected")
    public ResponseEntity<ApiResponse<List<ReportDTO>>> getAllReportStatusRejected() {
        List<ReportDTO> reportDTOS = reportService.getAllReportStatusRejected();
        ApiResponse<List<ReportDTO>> response = ApiResponse.<List<ReportDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched report")
                .data(reportDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/processing")
    public ResponseEntity<ApiResponse<List<ReportDTO>>> getAllReportStatusProcessing() {
        List<ReportDTO> reportDTOS = reportService.getAllReportStatusProcessing();
        ApiResponse<List<ReportDTO>> response = ApiResponse.<List<ReportDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched report")
                .data(reportDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ApiResponse<ReportDTO>> getReportDetail(@PathVariable int id){
        ReportDTO reportDTO = reportService.getReportDetail(id);
        ApiResponse<ReportDTO> response = ApiResponse.<ReportDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully fetched report")
                .data(reportDTO)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ReportDTO>> createReport(@RequestBody CreateReportRequest createReportRequest) {
        ReportDTO reportDTO = reportService.createReport(createReportRequest);
        ApiResponse<ReportDTO> response = ApiResponse.<ReportDTO>builder()
                .code(HttpStatus.CREATED.value())
                .message("Successfully created report")
                .data(reportDTO)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteReport(@PathVariable int id) {
        reportService.deleteReport(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully deleted report")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update_status_approved/{id}")
    public ResponseEntity<ApiResponse<String>> UpdateStatusReportApproved(@PathVariable int id) throws MessagingException {
        reportService.updateStatusReportApproved(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully updated report status")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update_status_rejected/{id}")
    public ResponseEntity<ApiResponse<String>> UpdateStatusReportRejected(@PathVariable int id) throws MessagingException {
        reportService.updateStatusReportRejected(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully updated report status")
                .build();
        return ResponseEntity.ok(response);
    }


}
