package com.miaoaotian.sys_back.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurnoverVO {
    private String value;
    private LocalDate time;
}
