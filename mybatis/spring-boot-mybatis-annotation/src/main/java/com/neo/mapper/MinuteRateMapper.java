package com.neo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

import com.neo.model.MinuteRate;

public interface MinuteRateMapper {
    
	@Select("SELECT * FROM bn_kline_gr_m WHERE start_time >= #{time} limit ${num}")
    List<MinuteRate> getByTimeNum(Long time,int num);

    
 /*   
    @Insert("INSERT INTO bn_kline_gr_m(start_time,t4) VALUES(#{userName}, #{passWord}, #{userSex})")
	void insert(MinuteRate mr);

	@Update("UPDATE users SET userName=#{userName},nick_name=#{nickName} WHERE id =#{id}")
	void update(MinuteRate mr);

*/

}