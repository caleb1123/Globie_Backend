package com.product.globie.service.impl;

import com.product.globie.config.Util;
import com.product.globie.entity.Product;
import com.product.globie.entity.Enum.EReportStatus;
import com.product.globie.entity.Report;
import com.product.globie.entity.User;
import com.product.globie.payload.DTO.ReportDTO;
import com.product.globie.payload.request.CreateReportRequest;
import com.product.globie.repository.ProductRepository;
import com.product.globie.repository.ReportRepository;
import com.product.globie.repository.UserRepository;
import com.product.globie.service.AccountService;
import com.product.globie.service.ReportService;
import jakarta.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportRepository reportRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    Util util;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    MailService mailService;

    @Autowired
    UserRepository userRepository;


    @Override
    public List<ReportDTO> getAllReport() {
        List<Report> reports = reportRepository.findAll();

        List<ReportDTO> reportDTOS = reports.stream()
                .map(report -> {
                    ReportDTO reportDTO = mapper.map(report, ReportDTO.class);

                    if (report.getProduct() != null) {
                        reportDTO.setProductId(report.getProduct().getProductId());
                    }
                    if (report.getUser() != null) {
                        reportDTO.setUserId(report.getUser().getUserId());
                    }

                    return reportDTO;
                })
                .collect(Collectors.toList());

        return reportDTOS.isEmpty() ? null : reportDTOS;
    }

    @Override
    public List<ReportDTO> getAllReportStatusApproved() {
        List<Report> reports = reportRepository.findAll();

        List<ReportDTO> reportDTOS = reports.stream()
                .filter(Report -> Report.getStatus().equals("Approved"))
                .map(report -> {
                    ReportDTO reportDTO = mapper.map(report, ReportDTO.class);

                    if (report.getProduct() != null) {
                        reportDTO.setProductId(report.getProduct().getProductId());
                    }
                    if (report.getUser() != null) {
                        reportDTO.setUserId(report.getUser().getUserId());
                    }

                    return reportDTO;
                })
                .collect(Collectors.toList());

        return reportDTOS.isEmpty() ? null : reportDTOS;
    }

    @Override
    public List<ReportDTO> getAllReportStatusRejected() {
        List<Report> reports = reportRepository.findAll();

        List<ReportDTO> reportDTOS = reports.stream()
                .filter(Report -> Report.getStatus().equals("Rejected"))
                .map(report -> {
                    ReportDTO reportDTO = mapper.map(report, ReportDTO.class);

                    if (report.getProduct() != null) {
                        reportDTO.setProductId(report.getProduct().getProductId());
                    }
                    if (report.getUser() != null) {
                        reportDTO.setUserId(report.getUser().getUserId());
                    }

                    return reportDTO;
                })
                .collect(Collectors.toList());

        return reportDTOS.isEmpty() ? null : reportDTOS;
    }

    @Override
    public List<ReportDTO> getAllReportStatusProcessing() {
        List<Report> reports = reportRepository.findAll();

        List<ReportDTO> reportDTOS = reports.stream()
                .filter(Report -> Report.getStatus().equals("Processing"))
                .map(report -> {
                    ReportDTO reportDTO = mapper.map(report, ReportDTO.class);

                    if (report.getProduct() != null) {
                        reportDTO.setProductId(report.getProduct().getProductId());
                    }
                    if (report.getUser() != null) {
                        reportDTO.setUserId(report.getUser().getUserId());
                    }

                    return reportDTO;
                })
                .collect(Collectors.toList());

        return reportDTOS.isEmpty() ? null : reportDTOS;
    }

    @Override
    public ReportDTO createReport(CreateReportRequest reportRequest) {
        Report report = new Report();
        report.setMessage(reportRequest.getMessage());
        report.setStatus(EReportStatus.Processing.name());
        report.setCreatedTime(new Date());
        report.setUser(util.getUserFromAuthentication());

        Product product = productRepository.findById(reportRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found wit Id: " + reportRequest.getProductId()));
        report.setProduct(product);

        Report savedReport = reportRepository.save(report);
        ReportDTO reportDTO = mapper.map(savedReport, ReportDTO.class);
        reportDTO.setProductId(savedReport.getProduct().getProductId());
        reportDTO.setUserId(savedReport.getUser().getUserId());

        return reportDTO;
    }

    @Override
    public void deleteReport(int rId) {
        Report report = reportRepository.findById(rId)
                .orElseThrow(() -> new RuntimeException("Report not found with Id: " + rId));

        reportRepository.delete(report);
    }

    @Override
    public void updateStatusReportApproved(int rId) throws MessagingException {
        Report report = reportRepository.findById(rId)
                .orElseThrow(() -> new RuntimeException("Report not found with Id: " + rId));
        report.setStatus(EReportStatus.Approved.name());
        report.setUpdatedTime(new Date());

        Product product = productRepository.findById(report.getProduct().getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with Id: " + report.getProduct().getProductId()));

        User user = userRepository.findById(product.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        List<Product> products = productRepository.findProductByUser(product.getUser().getUserId());

        int totalApprovedReportCount = 0;

        for (Product prod : products) {
            int approvedReportCount = reportRepository.countReportByStatusApproved(prod.getProductId());
            totalApprovedReportCount += approvedReportCount;

            if (totalApprovedReportCount >= 5) {
                accountService.updateStatusUserToFalse(prod.getUser().getUserId());
                mailService.SendEmailBannedStatusMember(user.getEmail(), user.getFullName());
                break;
            }
        }
        reportRepository.save(report);
    }

    @Override
    public void updateStatusReportRejected(int rId) {
        Report report = reportRepository.findById(rId)
                .orElseThrow(() -> new RuntimeException("Report not found with Id: " + rId));
        report.setStatus(EReportStatus.Rejected.name());
        report.setUpdatedTime(new Date());

        reportRepository.save(report);
    }

    @Override
    public ReportDTO getReportDetail(int rId) {
        Report report = reportRepository.findById(rId)
                .orElseThrow(() -> new RuntimeException("Report not found with Id: " + rId));

        ReportDTO reportDTO = mapper.map(report, ReportDTO.class);
        reportDTO.setProductId(report.getProduct().getProductId());
        reportDTO.setUserId(report.getUser().getUserId());

        return reportDTO;
    }
}
