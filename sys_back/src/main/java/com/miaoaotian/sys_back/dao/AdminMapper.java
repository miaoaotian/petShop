package com.miaoaotian.sys_back.dao;

import com.miaoaotian.sys_back.model.Admin;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminMapper {
    @Select("select * from admin where email = #{email}")
    Admin searchUser(String email);
    @Insert("insert into admin(username,password,email) values (#{username},#{password},#{email})")
    void insertUser(@Param("username") String username,@Param("password") String password,@Param("email") String email);
}
