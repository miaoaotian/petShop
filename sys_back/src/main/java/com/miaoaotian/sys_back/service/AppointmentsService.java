package com.miaoaotian.sys_back.service;

import com.miaoaotian.sys_back.model.Appointments;
import com.miaoaotian.sys_back.model.Food;
import com.miaoaotian.sys_back.model.Page;
import com.miaoaotian.sys_back.model.query.Query;
import com.miaoaotian.sys_back.model.vo.AppointmentTimeVO;

import java.util.List;

public interface AppointmentsService {
    List<Appointments> getAppointmentByTime(Integer sid,String date);
    List<AppointmentTimeVO> getAppointment(Integer sid);

    void setAppointment(Integer sid, String date,Integer mid);
}
