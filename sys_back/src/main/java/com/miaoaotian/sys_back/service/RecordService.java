package com.miaoaotian.sys_back.service;

import com.miaoaotian.sys_back.model.Page;
import com.miaoaotian.sys_back.model.Record;
import com.miaoaotian.sys_back.model.query.Query;
import com.miaoaotian.sys_back.model.vo.*;

import java.util.List;

public interface RecordService {
    void addRecord(Record record);

    List<Record> findAll(Page p, Query query);

    List<wordCloudVO> wordCloud();

    List<TurnoverVO> turnover();

    List<wordCloudVO> users();

    List<EveryMonthVO> year();

    DataVO Data();

    List<SalesVO> salesData();
}
