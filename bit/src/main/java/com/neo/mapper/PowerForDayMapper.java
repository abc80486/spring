package com.neo.mapper;

import com.neo.shiro.model.PowerForDay;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PowerForDayMapper {

    @Insert("INSERT INTO  Power_For_Day(time,wp1power,wp2power,wp3power,crew1power,crew2power,airconpower) VALUES(#{time},#{wp1power},#{wp2power},#{wp3power},#{crew1power},#{crew2power},#{airconpower})")
    public void insert(PowerForDay powerForDay);

    @Select("SELECT  id ,time, wp1power,wp2power,wp3power,crew1power,crew2power,airconpower From Power_For_Day where time>=#{data} limit 1")
    PowerForDay selectByTime(@Param("data") String data);

    @Select("SELECT  id ,time, wp1power,wp2power,wp3power,crew1power,crew2power,airconpower From Power_For_Day where time>=#{sdata} and time<#{edata} limit 1")
    PowerForDay selectByTimeRange(@Param("sdata") String sdata,@Param("edata") String edata);

    @Select("SELECT  id ,time, wp1power,wp2power,wp3power,crew1power,crew2power,airconpower From Power_For_Day where time=#{data} limit 1")
    PowerForDay selectByOneDay(@Param("data") String data);


}