package com.neo.mapper;

import java.util.Date;
import java.util.List;

import com.neo.model.ServicesCtrl;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface ServicesCtrlMapper {

    @Select("SELECT * FROM ServicesCtrl")
    @Results({ @Result(property = "id", column = "id"), @Result(property = "operation", column = "operation"),
            @Result(property = "mode", column = "mode"), @Result(property = "service", column = "service"),
            @Result(property = "time", column = "time") })
    List<ServicesCtrl> getAll();

    @Select("SELECT * FROM ServicesCtrl WHERE service = #{service}")
    @Results({ @Result(property = "id", column = "id"), @Result(property = "operation", column = "operation"),
            @Result(property = "mode", column = "mode"), @Result(property = "service", column = "service"),
            @Result(property = "time", column = "time") })
    List<ServicesCtrl> getService(short service);

    @Select("SELECT * FROM ServicesCtrl WHERE time>=#{startTime} and time<=#{endTime}")
    @Results({ @Result(property = "id", column = "id"), @Result(property = "operation", column = "operation"),
            @Result(property = "mode", column = "mode"), @Result(property = "service", column = "service"),
            @Result(property = "time", column = "time") })
    List<ServicesCtrl> getOperaInTime(String startTime, String endTime);

    @Insert("INSERT INTO ServicesCtrl(service,operation,mode) VALUES(#{service}, #{operation}, #{mode})")
    void insert(ServicesCtrl servicesCtrl);

    @Insert("INSERT INTO main_data(wp1_ctrl,wp1_status,wp1_mode,wp2_ctrl,wp2_status,wp2_mode,wp3_ctrl,wp3_status,wp3_mode,valve1_ctrl,valve1_open_status,valve1_close_status,valve1_mode,valve2_ctrl,valve2_open_status,valve2_close_status,valve2_mode) VALUES(#{test[0]},#{test[1]},#{test[2]},#{test[3]},#{test[4]},#{test[5]},#{test[6]},#{test[7]},#{test[8]},#{test[9]},#{test[10]},#{test[11]},#{test[12]},#{test[13]},#{test[14]},#{test[15]},#{test[16]})")
    void insertTest(@Param("test") List<Boolean> test);

    @Insert("INSERT INTO mdata(data) VALUES(#{data})")
    void insertStatusData(@Param("data") Long data);

}