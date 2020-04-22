package com.neo.services;

import com.neo.model.*;
import java.util.List;

public interface MinuteDataService {

    public  List<MinuteData> getByTimeNum(long time,int num) ;
}