package com.miaoaotian.sys_back.model.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 查询条件
 * 主打的时候一个复用
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Query {
    private String name; //会员，员工,宠物，食物
    private String phone; //会员，员工
    private String email; //会员，员工
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinDate; //会员，员工
    private Integer position;//员工
    private String breed;//宠物
    private Integer healthStatus;//宠物
    private Integer gender;//宠物
    private Integer status;//宠物
    private String brand;//食物
    private String type;//食物
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;//食物
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate time;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;
}
