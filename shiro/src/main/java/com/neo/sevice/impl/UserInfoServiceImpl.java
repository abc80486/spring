package com.neo.sevice.impl;

import com.neo.dao.UserInfoDao;
import com.neo.model.UserInfo;
import com.neo.sevice.UserInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

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