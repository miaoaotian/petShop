package com.miaoaotian.sys_back.service.impl;

import com.github.pagehelper.PageHelper;
import com.miaoaotian.sys_back.dao.FoodMapper;
import com.miaoaotian.sys_back.dao.MembersMapper;
import com.miaoaotian.sys_back.dao.RecordMapper;
import com.miaoaotian.sys_back.model.Page;
import com.miaoaotian.sys_back.model.Record;
import com.miaoaotian.sys_back.model.query.Query;
import com.miaoaotian.sys_back.model.vo.*;
import com.miaoaotian.sys_back.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RecordServiceImpl implements RecordService {
    private RecordMapper recordMapper;
    private FoodMapper foodMapper;
    private MembersMapper membersMapper;
    @Autowired
    public void setMembersMapper(MembersMapper membersMapper) {
        this.membersMapper = membersMapper;
    }
    @Autowired
    public void setFoodMapper(FoodMapper foodMapper) {
        this.foodMapper = foodMapper;
    }
    @Autowired
    public void setRecordMapper(RecordMapper recordMapper) {
        this.recordMapper = recordMapper;
    }
    @Override
    public void addRecord(Record record) {
        record.setTime(LocalDateTime.now());
        recordMapper.insert(record);
    }

    @Override
    public List<Record> findAll(Page p, Query query) {
        try(com.github.pagehelper.Page<?> __ = PageHelper.startPage(p.getPageNo(), p.getPageSize())) {
            return recordMapper.findAll(query);
        }
    }

    @Override
    public List<wordCloudVO> wordCloud() {
        Query q =  new Query();
        q.setType(String.valueOf(2));
        List<Record> records = recordMapper.findAll(q);
        Map<Integer, Integer> map = new HashMap<>();
        for (Record record : records) {
            if(map.containsKey(record.getOId())){
                int count = map.get(record.getOId());
                count+=record.getMoney();
                map.put(record.getOId(), count);
            }else {
                map.put(record.getOId(), record.getMoney());
            }
        }
        List<wordCloudVO> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            Integer id = entry.getKey();
            Integer value = entry.getValue();
            wordCloudVO vo = foodMapper.findByIdAndValue(id, value);
            if(vo != null) {
                list.add(vo);
            }
        }
        return list;
    }

    @Override
    public List<TurnoverVO> turnover() {
        LocalDate today = LocalDate.now();
        List<TurnoverVO> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = today.minusDays(i);
            TurnoverVO vo =  recordMapper.countAllMoney(date);
            if(vo == null) {
                vo = new TurnoverVO();
            }
            vo.setTime(date);
            list.add(vo);
        }
        return list;
    }

    @Override
    public List<wordCloudVO> users() {
        List<wordCloudVO> list = membersMapper.find5();
        return list;
    }

    @Override
    public List<EveryMonthVO> year() {
        List<EveryMonthVO> list = new ArrayList<>();
        for (int i = 11; i >= 0; i--) {  // 从当前月份往前数12个月
            LocalDate startOfMonth = LocalDate.now().minusMonths(i).withDayOfMonth(1);
            LocalDate startOfNextMonth = startOfMonth.plusMonths(1);
            EveryMonthVO vo = recordMapper.countAllMoneyByYear(startOfMonth, startOfNextMonth);
            if (vo != null) {
                vo.setMonth(startOfMonth.format(DateTimeFormatter.ofPattern("yyyy-MM")));
                list.add(vo);
            }
        }
        return list;
    }

    @Override
    public DataVO Data() {
        LocalDate today = LocalDate.now();
        int totalUser = membersMapper.count();
        int totalMoney = recordMapper.count();
        TurnoverVO turnoverVO  = recordMapper.countAllMoney(today);
        int todayMoney = 0;
        if(turnoverVO == null) {
            todayMoney = 0;
        } else {
            if(turnoverVO.getValue() != null) todayMoney = Integer.parseInt(turnoverVO.getValue());
        }
        int todayUser = membersMapper.countAllMoney(today);
        return new DataVO(todayUser, totalUser, todayMoney, totalMoney);
    }

    @Override
    public List<SalesVO> salesData() {
        return recordMapper.salesData();
    }
}
