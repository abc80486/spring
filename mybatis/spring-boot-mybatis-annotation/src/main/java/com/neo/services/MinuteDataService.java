package com.neo.services;

import com.neo.mapper.*;
import com.neo.model.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MinuteDataService {

    @Autowired
	private MinuteDataMapper data;

    public  List<MinuteData> getByTimeNum() {
		List<MinuteData> d = data.getByTimeNum(1587316500000l, 5);
		return d;
	}
}