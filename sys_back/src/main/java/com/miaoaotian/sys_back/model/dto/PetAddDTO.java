package com.miaoaotian.sys_back.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetAddDTO {
    private String name;
    private Integer gender;
    private String breed;
    private Integer age;
    private Integer price;
    private String description;
    private String photo;
}
