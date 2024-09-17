package com.miaoaotian.sys_back.controller;

import com.github.pagehelper.PageInfo;
import com.miaoaotian.sys_back.model.Appointments;
import com.miaoaotian.sys_back.model.Food;
import com.miaoaotian.sys_back.model.Page;
import com.miaoaotian.sys_back.model.query.Query;
import com.miaoaotian.sys_back.service.AppointmentsService;
import com.miaoaotian.sys_back.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/appointments", produces = "application/json;charset=utf-8")
@Slf4j
public class AppointmentsController {
    private AppointmentsService appointmentsService;
    @Autowired
    public void setAppointmentsService(AppointmentsService appointmentsService) {
        this.appointmentsService = appointmentsService;
    }
    @GetMapping("/getAppoints")
    public Result<List<Appointments>> getAppointmentByTime(@RequestParam("sid") Integer sid,@RequestParam("date") String date){
        log.info("date:"+date+",sid:"+sid);
        return Result.success(appointmentsService.getAppointmentByTime(sid,date));
    }
}
