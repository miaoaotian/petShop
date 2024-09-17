package com.miaoaotian.sys_back.service;

import com.miaoaotian.sys_back.model.Page;
import com.miaoaotian.sys_back.model.Staff;
import com.miaoaotian.sys_back.model.query.Query;

import java.util.List;

public interface StaffService {
    public List<Staff> findAll(Page p, Query query);
    // 新增
    public void addStaff(Staff staff);
    // 修改
    public void updateStaff(Staff staff);
    // 删除
    public void deleteStaffs(Integer[] ids);

    List<Staff> getDoctor();
}
