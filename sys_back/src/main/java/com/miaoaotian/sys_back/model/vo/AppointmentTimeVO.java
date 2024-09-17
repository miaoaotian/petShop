package com.miaoaotian.sys_back.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentTimeVO {
    private Integer id;
    private Integer sid;
    private String name;
    private String startTime;
    private String endTime;
    private Boolean status;
}
