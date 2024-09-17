package com.miaoaotian.sys_back.service.impl;

import com.github.pagehelper.PageHelper;
import com.miaoaotian.sys_back.dao.FoodMapper;
import com.miaoaotian.sys_back.model.Food;
import com.miaoaotian.sys_back.model.Page;
import com.miaoaotian.sys_back.model.query.Query;
import com.miaoaotian.sys_back.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {
    private FoodMapper foodMapper;
    @Autowired
    public void setFoodMapper(FoodMapper foodMapper) {
        this.foodMapper = foodMapper;
    }
    @Override
    public List<Food> findAll(Page p, Query query) {
        try(com.github.pagehelper.Page<?> __ = PageHelper.startPage(p.getPageNo(), p.getPageSize())) {
            return foodMapper.findAll(query);
        }
    }

    @Override
    public void addFood(Food food) {
        foodMapper.insert(food);
    }

    @Override
    public void updateFood(Food food) {
        foodMapper.update(food);
    }

    @Override
    public void deleteFoods(Integer[] ids) {
        foodMapper.deleteByIds(ids);
    }

    @Override
    public List<Food> findAllNoPage() {
        return foodMapper.findAll(null);
    }
}
