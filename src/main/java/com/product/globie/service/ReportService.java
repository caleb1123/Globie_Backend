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

    List<ReportDTO> getAllReportStatusApproved();

    List<ReportDTO> getAllReportStatusRejected();

    List<ReportDTO> getAllReportStatusProcessing();

    ReportDTO createReport(CreateReportRequest reportRequest);

    void deleteReport(int rId);

    void updateStatusReportApproved(int rId) throws MessagingException;

    void updateStatusReportRejected(int rId);


    ReportDTO getReportDetail(int rId);
}
