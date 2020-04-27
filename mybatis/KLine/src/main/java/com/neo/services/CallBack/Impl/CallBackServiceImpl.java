package com.neo.services.CallBack.Impl;

import com.neo.mapper.CallBackMapper;
import com.neo.model.CallBack;
import com.neo.services.CallBack.CallBackService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CallBackServiceImpl implements CallBackService {

    @Autowired
    CallBackMapper cbm;

    @Override
    public CallBack getDBLatelyData(int T, int k, int n) {
        return cbm.getDBLatelyData(T, k, n);
    }

    @Override
    public boolean updateCallBack(CallBack cb) {
        return cbm.update(cb);
    }

}