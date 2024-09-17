package com.miaoaotian.sys_back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    private Integer id;
    private String name;
    private String breed;
    private Integer age;
    private Integer gender;
    private Integer price;
    private String description;
    private Integer healthStatus;
    private String avatar;
    private Integer status;
    private String photo;//这一条单纯是为了修改图片时传入的
}
