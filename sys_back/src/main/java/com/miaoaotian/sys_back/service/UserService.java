package com.miaoaotian.sys_back.service;

import com.miaoaotian.sys_back.model.Member;

public interface UserService {
    public void setAppointment();
    public void buyPet();
    public void buyFood();

    Member login(String phone, String password);

    void buyPet(Integer petId, Integer price, Integer id);

    void buyFood(Integer fid, Integer nums, Integer price, Integer id);

}
