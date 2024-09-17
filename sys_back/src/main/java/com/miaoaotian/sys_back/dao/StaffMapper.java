package com.miaoaotian.sys_back.dao;

import com.miaoaotian.sys_back.model.Staff;
import com.miaoaotian.sys_back.model.query.Query;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StaffMapper {
    List<Staff> findAll(Query query);
    void insert(Staff staff);
    void update(Staff staff);
    void deleteByIds(@Param("ids") Integer[] ids);
    @Select("select * from staff where position = 1")
    List<Staff> getDoctor();
}