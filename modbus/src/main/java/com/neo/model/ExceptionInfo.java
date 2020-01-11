package com.neo.model;

import java.util.Date;

import lombok.Data;

@Data
public class ExceptionInfo {
    int id;
    Date time;
    String exceptionType;
    String exceptionDesc;
    boolean determine;
    String operatetor;
}