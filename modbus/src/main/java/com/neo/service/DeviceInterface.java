package com.neo.service;

public interface DeviceInterface {
    int getStatus();
    String getName();
    int getMode();
    void setMode(int mode);
    void setName(String name);
    void setStatus(int status);
}