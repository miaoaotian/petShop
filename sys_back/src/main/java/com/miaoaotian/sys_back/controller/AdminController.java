package com.miaoaotian.sys_back.controller;

import com.miaoaotian.sys_back.model.Admin;
import com.miaoaotian.sys_back.model.dto.AdminDTO;
import com.miaoaotian.sys_back.service.AdminService;
import com.miaoaotian.sys_back.service.EmailService;
import com.miaoaotian.sys_back.utils.CreateImageCode;
import com.miaoaotian.sys_back.utils.Result;
import com.miaoaotian.sys_back.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
@Slf4j
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private EmailService emailService;
    @Autowired
    public RedisTemplate redisTemplate;
    @PostMapping("/login")
    public Result<HashMap<String, Object>> doLogin(@RequestBody AdminDTO adminDTO) {
        String token = adminService.doLogin(adminDTO);
        Long tokenExpiration = TokenUtil.getExpirationTime(token);
        log.info("token:"+token+",tokenExpiration:"+tokenExpiration);
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("tokenExpiration", tokenExpiration);
        return Result.success(map);
    }
    @GetMapping("/sendEmail")
    public Result<?> sendEmail(@RequestParam("email") String email) throws MessagingException {
        emailService.sendEmailCode(email);
        return Result.success();
    }
    @PostMapping("/register")
    public Result<?> register(@RequestBody Admin admin) {
        log.info("user:"+admin.toString());
        adminService.doRegister(admin);
        return Result.success();
    }
//    @GetMapping("/userInfo")
//    public Result<?> getUserInfo(@RequestHeader("Authorization") String token) {
//        return Result.success(userService.getUserInfo(token));
//    }
    @GetMapping("/captchaImage")
    public Result<?> getCode() throws IOException {
        String uuid = UUID.randomUUID().toString();
        String redisKey = "captcha_code:" + uuid;
        // 使用 CreateImageCode 来生成验证码
        CreateImageCode imageCode = new CreateImageCode(130, 40, 4, 20);
        BufferedImage image = imageCode.getBuffImg();
        // 将验证码图片转换为字节数组
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] imageBytes = baos.toByteArray();
        String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
        String captchaText = imageCode.getCode();
        redisTemplate.opsForValue().set(redisKey, captchaText, 5, TimeUnit.MINUTES);
        // 构造返回结果，返回 UUID 和 Base64编码的图片给前端
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("uuid", uuid);
        resultMap.put("imageBase64", imageBase64);
        return Result.success(resultMap);
    }
}
