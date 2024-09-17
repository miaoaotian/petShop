package com.miaoaotian.sys_back.dao;

import com.miaoaotian.sys_back.model.Food;
import com.miaoaotian.sys_back.model.query.Query;
import com.miaoaotian.sys_back.model.vo.wordCloudVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
public interface FoodMapper {
    List<Food> findAll(Query query);
    void insert(Food food);
    void update(Food food);
    void deleteByIds(@Param("ids") Integer[] ids);
    @Update("update food set nums = nums - #{nums} where id = #{id} and nums >= #{nums}")
    void updateNum(@Param("id") Integer fid,@Param("nums") Integer nums);
    @Select("select * from food where id = #{fid}")
    Food findById(Integer fid);


    wordCloudVO findByIdAndValue(@Param("id") Integer id,@Param("value") Integer value);
}