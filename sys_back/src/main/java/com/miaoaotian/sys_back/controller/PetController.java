package com.miaoaotian.sys_back.controller;

import com.github.pagehelper.PageInfo;
import com.miaoaotian.sys_back.model.Page;
import com.miaoaotian.sys_back.model.Pet;
import com.miaoaotian.sys_back.model.dto.PetAddDTO;
import com.miaoaotian.sys_back.model.query.Query;
import com.miaoaotian.sys_back.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.miaoaotian.sys_back.service.PetService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/pet", produces = "application/json;charset=utf-8")
public class PetController {
    private PetService petService;
    @Autowired
    public void setPetService(PetService petService) {
        this.petService = petService;
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
        List<Pet> pets = petService.findAll(p, query);
        PageInfo<Pet> plf = new PageInfo<>(pets);
        Map<String, Object> map = Map.of("total", plf.getTotal(), "pageNo", plf.getPageNum(), "pageSize", plf.getPageSize());
        Map<String,Object> data = Map.of("list", pets, "pageInfo", map);
        return Result.success(data);
    }
    @PostMapping
    public Result<String> addPet(@RequestBody PetAddDTO petAddDTO){
        petService.addPet(petAddDTO);
        return Result.success("添加成功");
    }
    @PutMapping
    public Result<String> updatePet(@RequestBody Pet pet){
        petService.updatePet(pet);
        return Result.success("修改成功");
    }
    @DeleteMapping
    public Result<String> deletePets(@RequestBody Integer[] ids){
        petService.deletePets(ids);
        return Result.success("删除成功");
    }

}
