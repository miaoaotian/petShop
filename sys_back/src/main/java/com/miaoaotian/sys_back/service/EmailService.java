package com.miaoaotian.sys_back.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    public RedisTemplate redisTemplate;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendEmailCode(String toEmail) throws MessagingException {
        String key = "code_" + toEmail;
        if (redisTemplate.hasKey(key + ":lock")) {
            throw new IllegalStateException("请在60s后再发送");
        }
        String code = generateVerificationCode();
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject("邮箱验证码");

        String mailContent = "<div style='font-family: Arial, sans-serif; text-align: center;'>" +
                "<h1 style='color: #333;'>欢迎使用萌宠之家管理系统!</h1>" +
                "<p style='font-size: 16px; color: #555;'>使用下面的验证码来完成你的注册</p>" +
                "<h2 style='background-color: #f8f8f8; padding: 10px; border-radius: 4px; display: inline-block; border: 1px solid #ccc;'>"
                + code + "</h2>" +
                "<p style='font-size: 14px; color: #666;'>这个邮箱验证码有效期为1小时</p>" +
                "</div>";

        helper.setText(mailContent, true);

        mailSender.send(message);
        redisTemplate.opsForValue().set(key, code, 1, TimeUnit.HOURS);
        log.info("发送邮件验证码成功"+redisTemplate.opsForValue().get(key));
        redisTemplate.opsForValue().set(key + ":lock", "locked", 60, TimeUnit.SECONDS);
    }

    private String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }
}