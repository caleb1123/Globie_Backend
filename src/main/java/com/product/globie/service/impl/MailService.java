package com.product.globie.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendNewMail(String to, String subject, String body,String fullname) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body.replace("{recipient_email}", fullname), true); // Replace {recipient_email} with actual recipient email

        mailSender.send(message);
    }

    public void sendOTPtoActiveAccount(String to, String otp, String fullname) throws MessagingException {
        String subject = "OTP kích hoạt tài khoản - Globie";
        String body = "<html>" +
                "<body>" +
                "<h2 style=\"color: #0D6EFD;\">OTP kích hoạt tài khoản</h2>" +
                "<p>Xin chào " + fullname + ",</p>" +
                "<p>Chúng tôi đã nhận được yêu cầu kích hoạt tài khoản Globie của bạn liên kết với địa chỉ email này. Nếu bạn không yêu cầu thay đổi này, bạn có thể bỏ qua email này.</p>" +
                "<p>Để kích hoạt tài khoản của bạn, vui lòng sử dụng mã OTP sau:</p>" +
                "<h3 style=\"color: #0D6EFD;\">" + otp + "</h3>" +
                "<p>Mã OTP này sẽ hết hạn sau 15 phút.</p>" +
                "<p>Cảm ơn bạn đã đồng hành cùng Globie !</p>" +
                "<p>Trân trọng,<br/>Globie</p>" +
                "</body>" +
                "</html>";
        sendNewMail(to, subject, body, fullname);
    }

    public void sendOTPtoChangePasswordAccount(String to, String otp, String fullname) throws MessagingException {
        String subject = "OTP để đặt lại mật khẩu - Globie";
        String body = "<html>" +
                "<body>" +
                "<h2 style=\"color: #0D6EFD;\">Mã OTP</h2>" +
                "<p>Xin chào " + fullname + ",</p>" +
                "<p>Chúng tôi đã nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn. Sử dụng mã OTP dưới đây để đặt lại mật khẩu của bạn:</p>" +
                "<h3 style=\"color: #0D6EFD;\">" + otp + "</h3>" +
                "<p>Mã OTP này sẽ hết hạn sau 15 phút.</p>" +
                "<p>Trân trọng,<br/>Globie</p>" +
                "</body>" +
                "</html>";
        sendNewMail(to, subject, body, fullname);
    }
}
