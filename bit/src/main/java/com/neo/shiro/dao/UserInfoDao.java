package com.neo.shiro.dao;

import java.util.List;

import com.neo.shiro.model.UserInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoDao extends JpaRepository<UserInfo, Long> {
    /**通过username查找用户信息;*/
    public UserInfo findByUsername(String username);

    @Query(value = "select u.id, u.username from UserInfo u ")
    public List<UserInfo> findAll();
}