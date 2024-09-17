package com.miaoaotian.sys_back.service;

import com.miaoaotian.sys_back.model.Food;
import com.miaoaotian.sys_back.model.Page;
import com.miaoaotian.sys_back.model.Pet;
import com.miaoaotian.sys_back.model.query.Query;

import java.util.List;

public interface FoodService {
    public List<Food> findAll(Page p, Query query);
    // 新增
    public void addFood(Food food);
    // 修改
    public void updateFood(Food food);
    // 删除
    public void deleteFoods(Integer[] ids);

    List<Food> findAllNoPage();
}
