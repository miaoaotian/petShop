package com.miaoaotian.sys_back.dao;

import com.miaoaotian.sys_back.model.Member;
import com.miaoaotian.sys_back.model.query.Query;
import com.miaoaotian.sys_back.model.vo.wordCloudVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface MembersMapper {
    List<Member> findAll(Query query);
    void insert(Member m);
    void update(Member m);
    void deleteByIds(@Param("ids") Integer[] ids);
    Member getUserByPhone(String phone);
    @Update("update members set cost = cost + #{price} where id = #{id}")
    void updateCost(@Param("id") Integer id,@Param("price") Integer price);
    @Select("select name,cost value from members order by cost desc limit 5")
    List<wordCloudVO> find5();
    @Select("select count(id) from members")
    int count();

    int countAllMoney(LocalDate today);
}
