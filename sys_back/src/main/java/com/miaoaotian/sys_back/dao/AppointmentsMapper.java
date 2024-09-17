package com.miaoaotian.sys_back.dao;

import com.miaoaotian.sys_back.model.Appointments;
import com.miaoaotian.sys_back.model.query.Query;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface AppointmentsMapper {
    List<Appointments> getAppointmentByTime(@Param("sid") Integer sid,@Param("date") String date);
    @Insert("insert into appointments(mid, sid, date) VALUES (#{mid},#{sid},#{date})")
    void insert(Appointments appointments);
}