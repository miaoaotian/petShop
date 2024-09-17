package com.miaoaotian.sys_back.service;

import com.miaoaotian.sys_back.model.Page;
import com.miaoaotian.sys_back.model.Pet;
import com.miaoaotian.sys_back.model.dto.PetAddDTO;
import com.miaoaotian.sys_back.model.query.Query;

import java.util.List;

public interface PetService {
    public List<Pet> findAll(Page p, Query query);
    // 新增
    public void addPet(PetAddDTO petAddDTO);
    // 修改
    public void updatePet(Pet pet);
    // 删除
    public void deletePets(Integer[] ids);

    List<Pet> findAllPets();
}
