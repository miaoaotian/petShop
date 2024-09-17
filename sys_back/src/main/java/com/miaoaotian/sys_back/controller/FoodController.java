package com.miaoaotian.sys_back.controller;

import com.github.pagehelper.PageInfo;
import com.miaoaotian.sys_back.model.Food;
import com.miaoaotian.sys_back.model.Page;
import com.miaoaotian.sys_back.model.Pet;
import com.miaoaotian.sys_back.model.query.Query;
import com.miaoaotian.sys_back.service.FoodService;
import com.miaoaotian.sys_back.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/food", produces = "application/json;charset=utf-8")
public class FoodController {
    private FoodService foodService;
    @Autowired
    public void setFoodService(FoodService foodService) {
        this.foodService = foodService;
    }
    @GetMapping
    public Result<Map<String,Object>> findAll(
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            Query query
    ){
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        Page p = new Page(pageNo, pageSize);
        List<Food> foods = foodService.findAll(p, query);
        PageInfo<Food> plf = new PageInfo<>(foods);
        Map<String, Object> map = Map.of("total", plf.getTotal(), "pageNo", plf.getPageNum(), "pageSize", plf.getPageSize());
        Map<String,Object> data = Map.of("list", foods, "pageInfo", map);
        return Result.success(data);
    }
    @PostMapping
    public Result<String> addFood(@RequestBody Food food){
        foodService.addFood(food);
        return Result.success("添加成功");
    }
    @PutMapping
    public Result<String> updateFood(@RequestBody Food food){
        foodService.updateFood(food);
        return Result.success("修改成功");
    }
    @DeleteMapping
    public Result<String> deleteFoods(@RequestBody Integer[] ids){
        foodService.deleteFoods(ids);
        return Result.success("删除成功");
    }
}
