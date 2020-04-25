package com.neo.mapper;

import com.neo.model.MinuteData;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
//import org.apache.ibatis.annotations.Result;
//import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface MinuteDataMapper {

    @Insert("INSERT INTO  bn_kline_m("+
    "start_time,open_price,top_price,low_price,close_price,amount,end_time,quantity,exchange_lots,vol_amount,vol_quantity)"+ 
    "VALUES("+
    "${start_time},${open_price},${top_price},${low_price},${close_price},${amount},${end_time},${quantity},${exchange_lots},${vol_amount},${vol_quantity)}")
    MinuteData insert(MinuteData d);

    @Select("SELECT * FROM bn_kline_m WHERE start_time >= ${stime} and start_time < ${etime}")
    List<MinuteData> getBySDTime(long stime,long etime);

    //根据开始时间查询数据，不一定取得对应多的数据
	@Select("SELECT * FROM bn_kline_m WHERE start_time >= ${time} limit ${num}")
    List<MinuteData> getByTimeNum(long time,int num);
    
    @Select("SELECT * FROM bn_kline_m WHERE start_time >= ${value}")
    List<MinuteData> getBySTime(long time);

    //

}