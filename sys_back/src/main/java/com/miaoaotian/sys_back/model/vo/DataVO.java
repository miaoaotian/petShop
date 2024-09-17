package com.miaoaotian.sys_back.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataVO {
    private Integer todayUser;
    private Integer totalUser;
    private Integer todayMoney;
    private Integer totalMoney;
}
