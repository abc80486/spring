package com.neo.mapper;

import java.util.List;

import com.neo.model.ExceptionInfo;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Results;

public interface ExceptionInfoMapper {

    public boolean add(String type,String desc);
    //public boolean del(String type,String desc);
    public boolean update(int id);

    @Select("SELECT id,time,exception_type,exception_desc,determine,operatetor FROM exception_info limit #{count}")
    @Results({@Result(property = "id", column = "id"), @Result(property = "time", column = "time"),
    @Result(property = "exceptionType", column = "exception_type"), @Result(property = "exceptionDesc", column = "exception_desc"),
    @Result(property = "determine", column = "determine"),@Result(property = "operatetor", column = "operatetor")})
    List<ExceptionInfo> getForNum(int count);

}