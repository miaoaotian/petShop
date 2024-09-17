package com.miaoaotian.sys_back.service;

import com.miaoaotian.sys_back.dao.AdminMapper;
import com.miaoaotian.sys_back.model.Admin;
import com.miaoaotian.sys_back.model.dto.AdminDTO;
import com.miaoaotian.sys_back.utils.Constant;
import com.miaoaotian.sys_back.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    public RedisTemplate redisTemplate;
    public String doLogin(AdminDTO adminDTO) {
        String email = adminDTO.getEmail();
        String password = DigestUtils.md5DigestAsHex(adminDTO.getPassword().getBytes()).toUpperCase()+ Constant.pass_end;

        String code = adminDTO.getVerification().toLowerCase();

        String redisKey ="captcha_code:" + adminDTO.getCaptchaUUID();
        String realCode = (String) redisTemplate.opsForValue().get(redisKey);
        Admin admin = adminMapper.searchUser(email);

        if (admin == null){throw new RuntimeException("未找到该用户");}
        if (!admin.getPassword().equals(password)) {throw new RuntimeException("密码错误");}
        if (!code.equals(realCode.toLowerCase())) {throw new RuntimeException("验证码错误");}
        String token = TokenUtil.generateToken(Integer.parseInt(admin.getId()));
        return token;
    }
    public void doRegister(Admin admin) {
        String email = admin.getEmail();
        String username = admin.getUsername();
        String password = DigestUtils.md5DigestAsHex(admin.getPassword().getBytes()).toUpperCase()+ Constant.pass_end;
        Admin tempUser = adminMapper.searchUser(email);
        if (tempUser != null) {throw new RuntimeException("该邮箱已被注册");}
        String key = "code_"+email;
        String nowCode = (String) redisTemplate.opsForValue().get(key);
        if (nowCode == null) {throw new RuntimeException("验证码已过期");}
        if (!admin.getEmailVerification().equals(nowCode)) {throw new RuntimeException("验证码错误");}
        log.info(username + " " + password + " " + email);
        adminMapper.insertUser(username,password,email);
    }
}
