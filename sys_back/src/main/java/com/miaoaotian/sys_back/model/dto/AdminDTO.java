package com.miaoaotian.sys_back.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {
    private String email;
    private String password;
    private String verification;
    private String captchaUUID;
}
