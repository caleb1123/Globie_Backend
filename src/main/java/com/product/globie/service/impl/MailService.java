package com.product.globie.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendNewMail(String to, String subject, String body,String fullname) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("globietechgear@gmail.com");
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

    public void SendEmailBannedStatusMember(String to, String fullname) throws MessagingException {
        String subject = "Cấm khỏi nền tảng - Globie";
        String body = "<html>" +
                "<body>" +
                "<h2 style=\"color: #0D6EFD;\">Cấm Người Dùng</h2>" +
                "<p>Xin chào " + fullname + ",</p>" +
                "<p>Sau khi chúng tôi nhận được nhiều đơn khiếu nại về các sản phẩm của bạn trên nền tảng của chúng tôi.</p>" +
                "<p>Chúng tôi rất tiếc phải thông báo với bạn rằng bạn đã bị cấm sử dụng nền tảng Globie.</p>" +
                "<p>Nếu bạn có bất kỳ phản hồi nào, vui lòng trả lời mail này!</p>" +
                "<p>Trân trọng,<br/>Globie</p>" +
                "</body>" +
                "</html>";
        sendNewMail(to, subject, body, fullname);
    }

    public void SendEmailStoreMember(String to, String fullName, String userName, LocalDate startTime, LocalDate endTime, String memberPackage) throws MessagingException {
        String subject = "\uD83C\uDF89 Chúc mừng! Tài khoản của bạn đã được nâng cấp lên Cửa hàng trên Globie!";
        String body = "<html>" +
                "<body>" +
                "<h2 style=\"color: #0D6EFD;\">Cửa hàng trên Globie</h2>" +
                "<p>Xin chào " + fullName + ",</p>" +
                "<p>Chúng tôi rất vui mừng thông báo rằng tài khoản của bạn trên Globie đã được nâng cấp thành công lên Cửa hàng! Điều này mở ra một loạt các tính năng và lợi ích dành riêng cho các nhà bán hàng chuyên nghiệp trên nền tảng của chúng tôi.</p>" +
                "<p>*** Chi tiết tài khoản của bạn:\n</p>" +
                "<p>      . Tên tài khoản: "+ userName +"</p>" +
                "<p>      . Gói thành viên: "+ memberPackage +" </p>" +
                "<p>      . Thời hạn từ: " + startTime + " đến: " + endTime + "</p>" +
                "<p>Nếu bạn có bất kỳ phản hồi nào, vui lòng trả lời mail này!</p>" +
                "<p>Một lần nữa, xin chúc mừng và chào mừng bạn đến với cộng đồng các nhà bán hàng chuyên nghiệp trên Globie!</p>" +
                "<p>Trân trọng,<br/>Globie</p>" +
                "</body>" +
                "</html>";
        sendNewMail(to, subject, body, fullName);
    }


    public void SendEmailStoreMemberExpired(String to, String fullName, String userName, LocalDate startTime, LocalDate endTime, LocalDate afterEndTime, String memberPackage) throws MessagingException {
        String subject = "⏳ Thông báo: Tài khoản Cửa hàng của bạn trên Globie đã hết hạn";
        String body = "<html>" +
                "<body>" +
                "<h2 style=\"color: #0D6EFD;\">Cửa hàng trên Globie</h2>" +
                "<p>Xin chào " + fullName + ",</p>" +
                "<p>Chúng tôi muốn thông báo rằng tài khoản Cửa hàng của bạn trên Globie đã hết hạn kể từ ngày: "+ afterEndTime +".\n" +
                "\n</p>" +
                "<p>*** Chi tiết tài khoản của bạn:\n</p>" +
                "<p>. Tên tài khoản: "+ userName +"</p>" +
                "<p>. Gói thành viên: "+ memberPackage +" </p>" +
                "<p>. Thời hạn từ: " + startTime + " đến: " + endTime + "\n</p>" +
                "<p>*** Những thay đổi sau khi hết hạn:\n" +
                "<p>     . Tài khoản của bạn sẽ trở lại trạng thái người dùng thông thường.</p>" +
                "<p>     . Bạn sẽ không còn quyền truy cập vào các tính năng dành riêng cho Cửa hàng.</p>" +
                "<p>     . Các sản phẩm của bạn sẽ không còn được hiển thị nổi bật hoặc nhận các lợi ích quảng bá như trước.</p>" +
                "<p>Gia hạn ngay hôm nay để tiếp tục kinh doanh hiệu quả!\n</p>" +
                "<p>Nếu bạn có bất kỳ phản hồi nào, vui lòng trả lời mail này!</p>" +
                "<p>Rất mong được tiếp tục đồng hành cùng bạn!\n</p>" +
                "<p>Trân trọng,<br/>Globie</p>" +
                "</body>" +
                "</html>";
        sendNewMail(to, subject, body, fullName);
    }
}
