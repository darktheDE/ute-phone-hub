package com.utephonehub.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

/**
 * Email Service
 * Handle email sending for password reset, verification, etc.
 */
public class EmailService {
    
    private static final Logger logger = LogManager.getLogger(EmailService.class);
    
    // Static block to log when class is loaded
    static {
        System.out.println("EmailService class loaded!");
        LogManager.getLogger(EmailService.class).info("EmailService class loaded");
    }
    
    private final String mailHost;
    private final int mailPort;
    private final String mailUsername;
    private final String mailPassword;
    private final String mailFrom;
    
    public EmailService() {
        System.out.println("EmailService constructor called!");
        
        this.mailHost = getEnvOrProperty("MAIL_HOST", "smtp.gmail.com");
        this.mailPort = Integer.parseInt(getEnvOrProperty("MAIL_PORT", "587"));
        this.mailUsername = getEnvOrProperty("MAIL_USERNAME", "");
        // Remove spaces from password (Gmail App Password format has spaces)
        this.mailPassword = getEnvOrProperty("MAIL_PASSWORD", "").replace(" ", "");
        this.mailFrom = getEnvOrProperty("MAIL_FROM", "noreply@utephonehub.me");
        
        // Debug log with System.out to bypass logger issues
        System.out.println("EmailService initialized:");
        System.out.println("  Host: " + mailHost);
        System.out.println("  Port: " + mailPort);
        System.out.println("  Username: " + mailUsername);
        System.out.println("  HasPassword: " + (mailPassword != null && !mailPassword.isEmpty()));
        System.out.println("  Password length: " + (mailPassword != null ? mailPassword.length() : 0));
        
        // Also log normally
        logger.info("EmailService initialized - Host: {}, Port: {}, Username: {}, HasPassword: {}", 
                mailHost, mailPort, mailUsername, (mailPassword != null && !mailPassword.isEmpty()));
    }
    
    /**
     * Send forgot password email with OTP
     */
    public boolean sendForgotPasswordEmail(String toEmail, String otp) {
        String subject = "Mã xác nhận đặt lại mật khẩu - UTE Phone Hub";
        String htmlContent = buildForgotPasswordEmailTemplate(otp);
        
        return sendEmail(toEmail, subject, htmlContent);
    }
    
    /**
     * Send email verification code
     */
    public boolean sendVerificationEmail(String toEmail, String code) {
        String subject = "Xác nhận địa chỉ email - UTE Phone Hub";
        String htmlContent = buildVerificationEmailTemplate(code);
        
        return sendEmail(toEmail, subject, htmlContent);
    }
    
    /**
     * Send welcome email
     */
    public boolean sendWelcomeEmail(String toEmail, String fullName) {
        String subject = "Chào mừng đến với UTE Phone Hub!";
        String htmlContent = buildWelcomeEmailTemplate(fullName);
        
        return sendEmail(toEmail, subject, htmlContent);
    }
    
    /**
     * Send email
     */
    private boolean sendEmail(String toEmail, String subject, String htmlContent) {
        System.out.println("=== sendEmail called ===");
        System.out.println("To: " + toEmail);
        System.out.println("Subject: " + subject);
        
        logger.info("Attempting to send email to: {} with subject: {}", toEmail, subject);
        
        try {
            // Validate credentials
            if (mailUsername == null || mailUsername.isEmpty()) {
                System.out.println("ERROR: MAIL_USERNAME is empty!");
                logger.error("MAIL_USERNAME is not configured");
                return false;
            }
            if (mailPassword == null || mailPassword.isEmpty()) {
                System.out.println("ERROR: MAIL_PASSWORD is empty!");
                logger.error("MAIL_PASSWORD is not configured");
                return false;
            }
            
            System.out.println("Using SMTP server: " + mailHost + ":" + mailPort + " with username: " + mailUsername);
            logger.info("Using SMTP server: {}:{} with username: {}", mailHost, mailPort, mailUsername);
            
            // Setup mail properties
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", mailHost);
            props.put("mail.smtp.port", mailPort);
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");
            
            // Create session
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mailUsername, mailPassword);
                }
            });
            
            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailFrom, "UTE Phone Hub"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setContent(htmlContent, "text/html; charset=utf-8");
            
            // Send message
            System.out.println("Sending email message via Transport.send()...");
            logger.info("Sending email message...");
            Transport.send(message);
            
            System.out.println("Email sent successfully!");
            logger.info("Email sent successfully to: {}", toEmail);
            return true;
            
        } catch (MessagingException e) {
            System.out.println("MessagingException: " + e.getMessage());
            e.printStackTrace();
            logger.error("MessagingException while sending email to: {}", toEmail, e);
            logger.error("Error message: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
            logger.error("Failed to send email to: {}", toEmail, e);
            return false;
        }
    }
    
    /**
     * Generate random OTP (6 digits)
     */
    public String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
    
    /**
     * Build forgot password email template
     */
    private String buildForgotPasswordEmailTemplate(String otp) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }" +
                ".container { max-width: 600px; margin: 0 auto; padding: 20px; }" +
                ".header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 30px; text-align: center; border-radius: 10px 10px 0 0; }" +
                ".content { background: #f9f9f9; padding: 30px; border-radius: 0 0 10px 10px; }" +
                ".otp-box { background: white; border: 2px dashed #667eea; padding: 20px; margin: 20px 0; text-align: center; border-radius: 8px; }" +
                ".otp-code { font-size: 32px; font-weight: bold; color: #667eea; letter-spacing: 5px; }" +
                ".warning { color: #e74c3c; font-size: 14px; margin-top: 20px; }" +
                ".footer { text-align: center; padding: 20px; color: #666; font-size: 12px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='header'>" +
                "<h1>🔐 Đặt lại mật khẩu</h1>" +
                "</div>" +
                "<div class='content'>" +
                "<p>Xin chào,</p>" +
                "<p>Bạn đã yêu cầu đặt lại mật khẩu cho tài khoản UTE Phone Hub của mình.</p>" +
                "<p>Vui lòng sử dụng mã xác nhận dưới đây để đặt lại mật khẩu:</p>" +
                "<div class='otp-box'>" +
                "<div class='otp-code'>" + otp + "</div>" +
                "</div>" +
                "<p>Mã xác nhận này có hiệu lực trong <strong>5 phút</strong>.</p>" +
                "<p class='warning'>⚠️ Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.</p>" +
                "</div>" +
                "<div class='footer'>" +
                "<p>© 2025 UTE Phone Hub. All rights reserved.</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }
    
    /**
     * Build email verification template
     */
    private String buildVerificationEmailTemplate(String code) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }" +
                ".container { max-width: 600px; margin: 0 auto; padding: 20px; }" +
                ".header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 30px; text-align: center; border-radius: 10px 10px 0 0; }" +
                ".content { background: #f9f9f9; padding: 30px; border-radius: 0 0 10px 10px; }" +
                ".code-box { background: white; border: 2px solid #667eea; padding: 20px; margin: 20px 0; text-align: center; border-radius: 8px; }" +
                ".code { font-size: 28px; font-weight: bold; color: #667eea; letter-spacing: 3px; }" +
                ".footer { text-align: center; padding: 20px; color: #666; font-size: 12px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='header'>" +
                "<h1>✉️ Xác nhận Email</h1>" +
                "</div>" +
                "<div class='content'>" +
                "<p>Xin chào,</p>" +
                "<p>Cảm ơn bạn đã đăng ký tài khoản tại UTE Phone Hub!</p>" +
                "<p>Vui lòng sử dụng mã xác nhận dưới đây để hoàn tất đăng ký:</p>" +
                "<div class='code-box'>" +
                "<div class='code'>" + code + "</div>" +
                "</div>" +
                "<p>Mã xác nhận này có hiệu lực trong <strong>15 phút</strong>.</p>" +
                "</div>" +
                "<div class='footer'>" +
                "<p>© 2025 UTE Phone Hub. All rights reserved.</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }
    
    /**
     * Build welcome email template
     */
    private String buildWelcomeEmailTemplate(String fullName) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }" +
                ".container { max-width: 600px; margin: 0 auto; padding: 20px; }" +
                ".header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 30px; text-align: center; border-radius: 10px 10px 0 0; }" +
                ".content { background: #f9f9f9; padding: 30px; border-radius: 0 0 10px 10px; }" +
                ".button { background: #667eea; color: white; padding: 12px 30px; text-decoration: none; border-radius: 5px; display: inline-block; margin: 20px 0; }" +
                ".footer { text-align: center; padding: 20px; color: #666; font-size: 12px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='header'>" +
                "<h1>🎉 Chào mừng đến với UTE Phone Hub!</h1>" +
                "</div>" +
                "<div class='content'>" +
                "<p>Xin chào <strong>" + fullName + "</strong>,</p>" +
                "<p>Chào mừng bạn đến với UTE Phone Hub - nơi cung cấp điện thoại chất lượng cao với giá tốt nhất!</p>" +
                "<p>Tài khoản của bạn đã được tạo thành công. Bạn có thể bắt đầu khám phá các sản phẩm của chúng tôi ngay bây giờ.</p>" +
                "<center>" +
                "<a href='http://localhost:8080' class='button'>Khám phá ngay</a>" +
                "</center>" +
                "<p>Nếu bạn có bất kỳ câu hỏi nào, đừng ngần ngại liên hệ với chúng tôi.</p>" +
                "</div>" +
                "<div class='footer'>" +
                "<p>© 2025 UTE Phone Hub. All rights reserved.</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }
    
    /**
     * Get environment variable or system property
     */
    private String getEnvOrProperty(String key, String defaultValue) {
        String value = System.getenv(key);
        if (value == null) {
            value = System.getProperty(key);
        }
        return value != null ? value : defaultValue;
    }
}
