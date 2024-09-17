package com.miaoaotian.sys_back.dao;

import com.miaoaotian.sys_back.model.Pet;
import com.miaoaotian.sys_back.model.query.Query;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface PetMapper {
    List<Pet> findAll(Query query);
    void insert(Pet pet);
    void update(Pet pet);
    void deleteByIds(@Param("ids") Integer[] id);
    @Select("select * from pets where status = 0")
    List<Pet> findAllPets();
    @Update("update pets set status = 1 where id = #{id}")
    void updateStatus(@Param("id") Integer id);
}