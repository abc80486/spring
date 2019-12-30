package com.neo.shiro.sevice.impl;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

import com.neo.shiro.dao.UserInfoDao;
import com.neo.shiro.model.UserInfo;
import com.neo.shiro.sevice.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;
    @Override
    public UserInfo findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");
        return userInfoDao.findByUsername(username);
    }

    @Override
    public List<UserInfo> findAll() {
        System.out.println("UserInfoServiceImpl.findAll()");
        return  (List<UserInfo>) userInfoDao.findAll();
    }
    
}