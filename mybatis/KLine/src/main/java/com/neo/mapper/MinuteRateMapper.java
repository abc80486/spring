package com.neo.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

import com.neo.model.MinuteRate;

public interface MinuteRateMapper {
    

    @Insert("INSERT INTO bn_kline_gr_m  VALUES(${start_time}, ${end_time}, ${low_price},${high_price},${low_time},${high_time},${range_price})")
	void insert(MinuteRate d);

    @Delete("DELETE FROM bn_kline_gr_m WHERE start_time = ${time}")
    void delete(Long time);

	@Select("SELECT * FROM bn_kline_gr_m WHERE start_time >= ${time} limit ${num}")
    List<MinuteRate> getByTimeNum(Long time,int num);

    @Select("SELECT * FROM bn_kline_gr_m WHERE start_time >= ${time}")
    List<MinuteRate> getByTime(Long time);

    @Update("UPDATE bn_kline_gr_m SET start_time=${d.start_time},end_time=${d.end_time},low_price=${d.low_price},high_price=${d.high_price},low_time=${d.low_time},high_time=${d.high_time},range_price=${d.range_price} WHERE val=${val}")
	void update(MinuteRate d,int val);



}