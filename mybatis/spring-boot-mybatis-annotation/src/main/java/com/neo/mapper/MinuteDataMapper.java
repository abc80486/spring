package com.neo.mapper;

import com.neo.model.MinuteData;
import java.util.List;

//import org.apache.ibatis.annotations.Result;
//import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface MinuteDataMapper {

	@Select("SELECT * FROM bn_kline_m WHERE start_time >= #{time} limit ${num}")
	List<MinuteData> getByTimeNum(Long time,int num);

}