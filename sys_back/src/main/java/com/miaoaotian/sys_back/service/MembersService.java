package com.miaoaotian.sys_back.service;
import com.miaoaotian.sys_back.model.Member;
import com.miaoaotian.sys_back.model.Page;
import com.miaoaotian.sys_back.model.query.Query;

import java.util.List;
public interface MembersService {
    // 查询所有
    public List<Member> findAll(Page p, Query query);
    // 新增
    public void addMember(Member member);
    // 修改
    public void updateMember(Member member);
    // 删除
    public void deleteMembers(Integer[] ids);
}
