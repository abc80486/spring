package com.neo.mapper;

import java.util.Date;
import java.util.List;

import com.neo.model.ServicesCtrl;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
    void insertTest(@Param("test") List<Object> test);

    @Insert("INSERT INTO mdata(data) VALUES(#{data})")
    void insertStatusData(@Param("data") Long data);

    //@Insert("INSERT INTO analysis_data(wp1_rt_day,wp1_run_times_day,wp2_rt_day,wp2_run_times_day,wp3_rt_day,wp3_run_times_day,crew1_rt_day,crew1_run_times_day,crew2_rt_day,crew2_run_times_day,aircon_rt_day,aircon_run_times_day") VALUES())
    @Insert("INSERT INTO analysis_data(time) VALUES(#{date})")
    void insertTime(@Param("date") String date);

    @Select("SELECT time from analysis_data where time=#{date}")
    String queryTime(@Param("date") String date);

    //update
    @Update("UPDATE ANALYSIS_DATA SET wp1_rt_day = wp1_rt_day + #{data} WHERE time = #{date}")
    void updateDataWP1(@Param("data") long data,@Param("date") String date);
    @Update("UPDATE ANALYSIS_DATA SET wp1_run_times_day = wp1_run_times_day + 1 WHERE time = #{date}")
    void updateDataWP1Times(@Param("date") String date);

    @Update("UPDATE ANALYSIS_DATA SET wp2_rt_day = wp2_rt_day + #{data} WHERE time = #{date}")
    void updateDataWP2(@Param("data") long data,@Param("date") String date);
    @Update("UPDATE ANALYSIS_DATA SET wp2_run_times_day = wp2_run_times_day + 1 WHERE time = #{date}")
    void updateDataWP2Times(@Param("date") String date);

    @Update("UPDATE ANALYSIS_DATA SET wp3_rt_day = wp3_rt_day + #{data} WHERE time = #{date}")
    void updateDataWP3(@Param("data") long data,@Param("date") String date);
    @Update("UPDATE ANALYSIS_DATA SET wp3_run_times_day = wp3_run_times_day + 1 WHERE time = #{date}")
    void updateDataWP3Times(@Param("date") String date);

    @Update("UPDATE ANALYSIS_DATA SET crew1_rt_day = crew1_rt_day + #{data} WHERE time = #{date}")
    void updateDataCrew1(@Param("data") long data,@Param("date") String date);
    @Update("UPDATE ANALYSIS_DATA SET crew1_run_times_day = crew1_run_times_day + 1 WHERE time = #{date}")
    void updateDataCrew1Times(@Param("date") String date);

    @Update("UPDATE ANALYSIS_DATA SET crew2_rt_day = crew2_rt_day + #{data} WHERE time = #{date}")
    void updateDataCrew2(@Param("data") long data,@Param("date") String date);
    @Update("UPDATE ANALYSIS_DATA SET crew2_run_times_day = crew2_run_times_day + 1 WHERE time = #{date}")
    void updateDataCrew2Times(@Param("date") String date);

    @Update("UPDATE ANALYSIS_DATA SET Aircon_rt_day = Aircon_rt_day + #{data} WHERE time = #{date}")
    void updateDataAircon(@Param("data") long data,@Param("date") String date);
    @Update("UPDATE ANALYSIS_DATA SET Aircon_run_times_day = Aircon_run_times_day + 1 WHERE time = #{date}")
    void updateDataAirconTimes(@Param("date") String date);

    @Update("UPDATE ANALYSIS_DATA SET WP1_RT_DAY = wp1_rt_day + #{data[0]},wp1_run_times_day = wp1_run_times_day + #{data[1]} WHERE time = #{data[2]")
    void updatawp1(@Param("data") List<String> data);

    




}