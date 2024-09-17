package com.miaoaotian.sys_back.service.impl;

import com.github.pagehelper.PageHelper;
import com.miaoaotian.sys_back.dao.StaffMapper;
import com.miaoaotian.sys_back.model.Page;
import com.miaoaotian.sys_back.model.Staff;
import com.miaoaotian.sys_back.model.query.Query;
import com.miaoaotian.sys_back.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {
    private StaffMapper staffMapper;
    @Autowired
    public void setStaffMapper(StaffMapper staffMapper) {
        this.staffMapper = staffMapper;
    }
    @Override
    public List<Staff> findAll(Page p, Query query) {
        try(com.github.pagehelper.Page<?> __ = PageHelper.startPage(p.getPageNo(), p.getPageSize())) {
            return staffMapper.findAll(query);
        }
    }
    @Override
    public void addStaff(Staff staff) {
        staff.setJoinDate(LocalDateTime.now());
        staffMapper.insert(staff);
    }
    @Override
    public void updateStaff(Staff staff) {
        staffMapper.update(staff);
    }
    @Override
    public void deleteStaffs(Integer[] ids) {
        staffMapper.deleteByIds(ids);
    }

    @Override
    public List<Staff> getDoctor() {
        return staffMapper.getDoctor();
    }
}
