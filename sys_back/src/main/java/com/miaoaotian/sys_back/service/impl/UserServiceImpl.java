package com.miaoaotian.sys_back.service.impl;

import com.miaoaotian.sys_back.dao.FoodMapper;
import com.miaoaotian.sys_back.dao.MembersMapper;
import com.miaoaotian.sys_back.dao.PetMapper;
import com.miaoaotian.sys_back.model.Member;
import com.miaoaotian.sys_back.model.Record;
import com.miaoaotian.sys_back.service.RecordService;
import com.miaoaotian.sys_back.service.UserService;
import com.miaoaotian.sys_back.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private MembersMapper membersMapper;
    private PetMapper petMapper;
    private RecordService recordService;
    private FoodMapper foodMapper;
    @Autowired
    public void setFoodMapper(FoodMapper foodMapper) {
        this.foodMapper = foodMapper;
    }
    @Autowired
    public void setRecordService(RecordService recordService) {
        this.recordService = recordService;
    }
    @Autowired
    public void setMembersMapper(MembersMapper membersMapper) {
        this.membersMapper = membersMapper;
    }
    @Autowired
    public void setPetMapper(PetMapper petMapper) {
        this.petMapper = petMapper;
    }
    @Override
    public void setAppointment() {

    }

    @Override
    public void buyPet() {

    }

    @Override
    public void buyFood() {

    }

    @Override
    public Member login(String phone, String password) {
        Member user = membersMapper.getUserByPhone(phone);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if(user.getPass() == null) {
            if(password.equals("123")) {
                return user;
            } else {
                throw new RuntimeException("密码错误");
            }
        }
        String pass = DigestUtils.md5DigestAsHex(password.getBytes()).toUpperCase() + Constant.pass_end;
        if(user.getPass().equals(pass)) {
            return user;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void buyPet(Integer petId, Integer price, Integer id) {
        membersMapper.updateCost(id, price);
        petMapper.updateStatus(petId);
        Record record = new Record();
        record.setMemId(id);
        record.setMoney(price);
        record.setType(0);
        record.setOId(petId);
        record.setDescription("购买了"+petId+"号宠物");
        recordService.addRecord(record);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void buyFood(Integer fid, Integer nums, Integer price, Integer id) {
        if(foodMapper.findById(fid).getNums() < nums) {
            throw new RuntimeException("库存不足");
        }
        membersMapper.updateCost(id, price*nums);
        foodMapper.updateNum(fid, nums);
        Record record = new Record();
        record.setMemId(id);
        record.setMoney(price*nums);
        record.setType(2);
        record.setOId(fid);
        record.setDescription("购买了"+nums+"袋"+fid+"号食品");
        recordService.addRecord(record);
    }
}
