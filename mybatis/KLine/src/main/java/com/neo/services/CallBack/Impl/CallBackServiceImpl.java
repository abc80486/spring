package com.neo.services.CallBack.Impl;

import com.neo.mapper.CallBackMapper;
import com.neo.model.CallBack;
import com.neo.services.CallBack.CallBackService;
import com.neo.services.MinuteDataService.MinuteDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CallBackServiceImpl implements CallBackService {

    @Autowired
    CallBackMapper cbm;

    @Autowired
    private MinuteDataService mds;

    @Override
    public CallBack getDBLatelyData(int T, int k, int n) {
        return cbm.getDBLatelyData(T, k, n);
    }

    @Override
    public boolean updateCallBack(CallBack cb) {
        return cbm.update(cb);
    }

    @Override
    public boolean updateCallBack(long stime, long etime, int T, int k, int n) {
        return updateCallBack(mds.getCallBackPro(stime, etime, T, k, n));
    }
}