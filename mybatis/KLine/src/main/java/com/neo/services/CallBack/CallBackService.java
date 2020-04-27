package com.neo.services.CallBack;

import com.neo.model.CallBack;

public interface CallBackService {

    CallBack getDBLatelyData(int T,int k,int n);

    boolean updateCallBack(CallBack cb);

}