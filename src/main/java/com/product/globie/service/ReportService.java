package com.product.globie.service;

import com.product.globie.payload.DTO.PostDTO;
import com.product.globie.payload.DTO.ReportDTO;
import com.product.globie.payload.request.CreatePostRequest;
import com.product.globie.payload.request.CreateReportRequest;
import com.product.globie.payload.request.UpdatePostRequest;
import jakarta.mail.MessagingException;

import java.util.List;

public interface ReportService {
    List<ReportDTO> getAllReport();

    List<ReportDTO> getAllReportStatusTrue();

    List<ReportDTO> getAllReportStatusFalse();

    ReportDTO createReport(CreateReportRequest reportRequest);

    void deleteReport(int rId);

    void updateStatusReport(int rId) throws MessagingException;

    ReportDTO getReportDetail(int rId);
}
