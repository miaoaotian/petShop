package com.miaoaotian.sys_back.controller;

import com.github.pagehelper.PageInfo;
import com.miaoaotian.sys_back.model.Member;
import com.miaoaotian.sys_back.model.Page;
import com.miaoaotian.sys_back.model.Staff;
import com.miaoaotian.sys_back.model.query.Query;
import com.miaoaotian.sys_back.service.StaffService;
import com.miaoaotian.sys_back.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/staff", produces = "application/json;charset=utf-8")
public class StaffController {
    private StaffService staffService;
    @Autowired
    public void setStaffService(StaffService staffService) {
        this.staffService = staffService;
    }
    @GetMapping
    public Result<Map<String,Object>> findAll(
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            Query query
    ){
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        Page p = new Page(pageNo, pageSize);
        List<Staff> staffs = staffService.findAll(p, query);
        PageInfo<Staff> plf = new PageInfo<>(staffs);
        Map<String, Object> map = Map.of("total", plf.getTotal(), "pageNo", plf.getPageNum(), "pageSize", plf.getPageSize());
        Map<String,Object> data = Map.of("list", staffs, "pageInfo", map);
        return Result.success(data);
    }
    @PostMapping
    public Result<String> addStaff(@RequestBody Staff staff){
        staffService.addStaff(staff);
        return Result.success("添加成功");
    }
    @PutMapping
    public Result<String> updateStaff(@RequestBody Staff staff){
        staffService.updateStaff(staff);
        return Result.success("修改成功");
    }
    @DeleteMapping
    public Result<String> deleteStaffs(@RequestBody Integer[] ids){
        staffService.deleteStaffs(ids);
        return Result.success("删除成功");
    }
    @GetMapping("/getDoctor")
    public Result<List<Staff>> getDoctor(){
        List<Staff> staffs = staffService.getDoctor();
        return Result.success(staffs);
    }
}
