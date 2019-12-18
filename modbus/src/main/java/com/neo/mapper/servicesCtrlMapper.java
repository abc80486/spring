package com.neo.mapper;

import java.util.Date;
import java.util.List;

import com.neo.model.ServicesCtrl;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface ServicesCtrlMapper {

    @Select("SELECT * FROM ServicesCtrl")
    @Results({
		@Result(property = "id",  column = "id"),
        @Result(property = "operation", column = "operation"),
        @Result(property = "mode",  column = "mode"),
        @Result(property = "service", column = "service"),
        @Result(property = "time",column = "time")
	})
    List<ServicesCtrl> getAll();

    @Select("SELECT * FROM ServicesCtrl WHERE service = #{service}")
    @Results({
		@Result(property = "id",  column = "id"),
        @Result(property = "operation", column = "operation"),
        @Result(property = "mode",  column = "mode"),
        @Result(property = "service", column = "service"),
        @Result(property = "time",column = "time")
    })
    List<ServicesCtrl> getService(short service);

    @Select("SELECT * FROM ServicesCtrl WHERE time>=startTime && time<=endTime")
    @Results({
		@Result(property = "id",  column = "id"),
        @Result(property = "operation", column = "operation"),
        @Result(property = "mode",  column = "mode"),
        @Result(property = "service", column = "service"),
        @Result(property = "time",column = "time")
    })
    List<ServicesCtrl> getOperaInTime(Date startTime,Date endTime);


    @Insert("INSERT INTO ServicesCtrl(service,operation,mode) VALUES(#{service}, #{operation}, #{mode})")
	void insert(ServicesCtrl servicesCtrl);

}