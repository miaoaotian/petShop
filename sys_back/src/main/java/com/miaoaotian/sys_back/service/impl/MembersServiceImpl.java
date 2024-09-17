package com.miaoaotian.sys_back.service.impl;

import com.github.pagehelper.PageHelper;
import com.miaoaotian.sys_back.dao.MembersMapper;
import com.miaoaotian.sys_back.model.Member;
import com.miaoaotian.sys_back.model.Page;
import com.miaoaotian.sys_back.model.query.Query;
import com.miaoaotian.sys_back.service.MembersService;
import com.miaoaotian.sys_back.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class MembersServiceImpl implements MembersService {
    private MembersMapper membersMapper;
    @Autowired
    public void setMembersMapper(MembersMapper membersMapper) {
        this.membersMapper = membersMapper;
    }
    @Override
    public List<Member> findAll(Page p, Query query) {
        try(com.github.pagehelper.Page<?> __ = PageHelper.startPage(p.getPageNo(), p.getPageSize())) {

            return membersMapper.findAll(query);
        }
    }

    @Override
    public void addMember(Member member) {
        member.setCost(0);
        member.setLevel(0);
        member.setJoinDate(LocalDateTime.now());
        member.setLastTime(LocalDateTime.now());
        if(member.getPass() != null) {
            String pass = DigestUtils.md5DigestAsHex(member.getPass().getBytes()).toUpperCase() + Constant.pass_end;
            member.setPass(pass);
        }
        log.info("member: {}", member);
        membersMapper.insert(member);
    }

    @Override
    public void updateMember(Member member) {
        membersMapper.update(member);
    }

    @Override
    public void deleteMembers(Integer[] ids) {
        membersMapper.deleteByIds(ids);
    }
}
