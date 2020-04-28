package com.neo.services.CallBack;

import com.neo.model.CallBack;

public interface CallBackService {

    /*
    T周期内振幅为f%,kT时间内回调f/n的概率为P
    */
    CallBack getDBLatelyData(int T,int k,int n);

    boolean updateCallBack(CallBack cb);

    boolean updateCallBack(long stime, long etime, int T, int k, int n);

    
}