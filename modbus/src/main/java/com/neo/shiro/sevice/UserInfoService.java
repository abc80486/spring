package com.neo.shiro.sevice;

import java.util.List;

import com.neo.shiro.model.UserInfo;



public interface UserInfoService {
    /**通过username查找用户信息;*/
    public UserInfo findByUsername(String username);
    
    public List<UserInfo> findAll();
}