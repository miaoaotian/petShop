package com.miaoaotian.sys_back.dao;
import com.miaoaotian.sys_back.model.Record;
import com.miaoaotian.sys_back.model.query.Query;
import com.miaoaotian.sys_back.model.vo.EveryMonthVO;
import com.miaoaotian.sys_back.model.vo.SalesVO;
import com.miaoaotian.sys_back.model.vo.TurnoverVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface RecordMapper {
    @Insert("INSERT INTO record (type, money, time, memId, oId, description) " +
            "VALUES (#{type}, #{money}, #{time}, #{memId}, #{oId}, #{description})")
    void insert(Record record);

    List<Record> findAll(Query query);


    TurnoverVO countAllMoney(LocalDate date);

    @Select("SELECT SUM(money) as value, DATE_FORMAT(time, '%Y-%m') as month " +
            "FROM record " +
            "WHERE time >= #{startOfMonth} AND time < #{startOfNextMonth} " +
            "GROUP BY DATE_FORMAT(time, '%Y-%m')")
    EveryMonthVO countAllMoneyByYear(@Param("startOfMonth") LocalDate startOfMonth,
                                     @Param("startOfNextMonth") LocalDate startOfNextMonth);
    @Select("select sum(money) from record")
    int count();
    @Select("select f.name,sum(r.money)/f.price as sales from record r join food f on f.id = r.oId where r.type = 2 GROUP BY r.oId ORDER BY sales desc limit 10;")
    List<SalesVO> salesData();
}
