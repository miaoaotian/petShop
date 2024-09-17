package com.miaoaotian.sys_back.controller;

import com.github.pagehelper.PageInfo;
import com.miaoaotian.sys_back.model.Page;
import com.miaoaotian.sys_back.model.Pet;
import com.miaoaotian.sys_back.model.Record;
import com.miaoaotian.sys_back.model.query.Query;
import com.miaoaotian.sys_back.model.vo.*;
import com.miaoaotian.sys_back.service.RecordService;
import com.miaoaotian.sys_back.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping(value="/record", produces = "application/json;charset=utf-8")
public class RecordController {
    private RecordService recordService;
    @Autowired
    public void setRecordService(RecordService recordService) {
        this.recordService = recordService;
    }
    @GetMapping
    public Result<Map<String,Object>> findAll(
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            Query query
    ){
        if(query.getStart() != null) {
            log.warn("查询开始时间：" + query.getStart() + " " +query.getStart().getClass().getName());
        }
        if(query.getEnd() != null){
            log.warn("查询结束时间：" + query.getEnd());
        }

        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        if( query.getStart() != null && query.getEnd() == null){
            System.out.println(query.getStart());
        }
        Page p = new Page(pageNo, pageSize);
        List<Record> records = recordService.findAll(p, query);
        PageInfo<Record> plf = new PageInfo<>(records);
        Map<String, Object> map = Map.of("total", plf.getTotal(), "pageNo", plf.getPageNum(), "pageSize", plf.getPageSize());
        Map<String,Object> data = Map.of("list", records, "pageInfo", map);
        return Result.success(data);
    }

    @GetMapping("/wordCloud")
    public Result<?> wordCloud(){
        List<wordCloudVO> records = recordService.wordCloud();
        return Result.success(records);
    }

    @GetMapping("/turnover")
    public Result<?> turnover(){
        List<TurnoverVO> records = recordService.turnover();
        return Result.success(records);
    }

    @GetMapping("/users")
    public Result<?> users(){
        List<wordCloudVO> records = recordService.users();
        return Result.success(records);
    }
    @GetMapping("/year")
    public Result<?> year(){
        List<EveryMonthVO> records = recordService.year();
        return Result.success(records);
    }

    @GetMapping("/Data")
    public Result<?> Data() {
        DataVO record = recordService.Data();
        return Result.success(record);
    }
    @GetMapping("/salesData")
    public Result<?> salesData() {
        List<SalesVO> records = recordService.salesData();
        return Result.success(records);
    }
}
