package com.miaoaotian.sys_back.controller;

import com.github.pagehelper.PageInfo;
import com.miaoaotian.sys_back.model.Member;
import com.miaoaotian.sys_back.model.Page;
import com.miaoaotian.sys_back.model.query.Query;
import com.miaoaotian.sys_back.service.MembersService;
import com.miaoaotian.sys_back.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value="/members", produces = "application/json;charset=utf-8")
public class MembersController {
    private MembersService membersService;
    @Autowired
    public void setMembersService(MembersService membersService) {
        this.membersService = membersService;
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
        List<Member> members = membersService.findAll(p, query);
        PageInfo<Member> plf = new PageInfo<>(members);
        Map<String, Object> map = Map.of("total", plf.getTotal(), "pageNo", plf.getPageNum(), "pageSize", plf.getPageSize());
        Map<String,Object> data = Map.of("list", members, "pageInfo", map);
        return Result.success(data);
    }
    @PostMapping
    public Result<String> addMember(@RequestBody Member member){
        membersService.addMember(member);
        return Result.success("添加成功");
    }
    @PutMapping
    public Result<String> updateMember(@RequestBody Member member){
        membersService.updateMember(member);
        return Result.success("修改成功");
    }
    @DeleteMapping
    public Result<String> deleteMembers(@RequestBody Integer[] ids){
        membersService.deleteMembers(ids);
        return Result.success("删除成功");
    }
}
