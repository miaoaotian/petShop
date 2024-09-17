package com.miaoaotian.sys_back.service.impl;
import com.miaoaotian.sys_back.dao.AppointmentsMapper;
import com.miaoaotian.sys_back.dao.MembersMapper;
import com.miaoaotian.sys_back.model.Appointments;
import com.miaoaotian.sys_back.model.Record;
import com.miaoaotian.sys_back.model.vo.AppointmentTimeVO;
import com.miaoaotian.sys_back.service.AppointmentsService;
import com.miaoaotian.sys_back.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class AppointmentsServiceImpl implements AppointmentsService {
    private MembersMapper membersMapper;
    private RecordService recordService;
    private AppointmentsMapper appointmentsMapper;
    @Autowired
    public void setAppointmentsMapper(AppointmentsMapper appointmentsMapper) {
        this.appointmentsMapper = appointmentsMapper;
    }
    @Autowired
    public void setMembersMapper(MembersMapper membersMapper) {
        this.membersMapper = membersMapper;
    }
    @Autowired
    public void setRecordService(RecordService recordService) {
        this.recordService = recordService;
    }
    @Override
    public List<Appointments> getAppointmentByTime(Integer sid,String date) {
        System.out.println(appointmentsMapper.getAppointmentByTime(sid,date));
        return appointmentsMapper.getAppointmentByTime(sid,date);
    }

    @Override
    public List<AppointmentTimeVO> getAppointment(Integer sid) {
        LocalDate today = LocalDate.now();
        List<Appointments> appointments = appointmentsMapper.getAppointmentByTime(sid, today.toString());
        List<AppointmentTimeVO> appointmentTimeVOS = new ArrayList<>();
        // 定义时间块
        LocalTime[][] timeBlocks = new LocalTime[][]{
                {LocalTime.of(8, 0, 0), LocalTime.of(8, 59, 59)},
                {LocalTime.of(9, 0, 0), LocalTime.of(9, 59, 59)},
                {LocalTime.of(10, 0, 0), LocalTime.of(10, 59, 59)},
                {LocalTime.of(11, 0, 0), LocalTime.of(11, 59, 59)},
                {LocalTime.of(13, 0, 0), LocalTime.of(13, 59, 59)},
                {LocalTime.of(14, 0, 0), LocalTime.of(14, 59, 59)},
                {LocalTime.of(15, 0, 0), LocalTime.of(15, 59, 59)},
                {LocalTime.of(16, 0, 0), LocalTime.of(16, 59, 59)},
                {LocalTime.of(18, 0, 0), LocalTime.of(18, 59, 59)},
                {LocalTime.of(19, 0, 0), LocalTime.of(19, 59, 59)},
                {LocalTime.of(20, 0, 0), LocalTime.of(20, 59, 59)},
                {LocalTime.of(21, 0, 0), LocalTime.of(21, 59, 59)}
        };
        for (LocalTime[] block : timeBlocks) {
            AppointmentTimeVO vo = new AppointmentTimeVO();
            vo.setSid(sid);
            vo.setStartTime(block[0].toString());
            vo.setEndTime(block[1].toString());
            // 检查当前时间块是否有预约
            boolean isBooked = false;
            for (Appointments appointment : appointments) {
                String hour = appointment.getDate().toString().split("T")[1].split(":")[0];
                String hour2 = block[0].toString().split(":")[0];
                if(hour.equals(hour2)) {
                    isBooked = true;
                    break;
                }
            }
            vo.setStatus(isBooked);
            vo.setName(isBooked ? "已被预约" : "暂无预约");
            appointmentTimeVOS.add(vo);
        }
        return appointmentTimeVOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setAppointment(Integer sid, String date, Integer mid) {
        Appointments appointments = new Appointments();
        appointments.setMid(mid);
        appointments.setSid(sid);
        String hour = date.split(":")[0];
        LocalDate localDate = LocalDate.now();
        String time = localDate + "T" + hour + ":00:00";
        appointments.setDate(LocalDateTime.parse(time));
        Record record = new Record();
        record.setMemId(mid);
        record.setMoney(500);
        record.setType(1);
        record.setOId(sid);
        record.setDescription("预约了"+sid+"号医生的时间");
        recordService.addRecord(record);
        appointmentsMapper.insert(appointments);
    }

}
