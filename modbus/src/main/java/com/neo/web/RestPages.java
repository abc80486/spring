package com.neo.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class RestPages {
    @PostMapping("/coil_ctrl")
    public boolean setCoil(int addr, boolean status) {
        ScadaImpl.setCoil(addr, status);
        return status;
    }

    @PostMapping("/registerCtrl")
    public boolean setRegister(int addr, float data) {
        // System.out.println(data);
        int temp;
        if (data < 0.0 || data > 100.0)
            return false;
        temp = Math.round(data * 10);
        return ScadaImpl.setRegister(addr, temp);
    }
    @GetMapping("/getHFData")
    public List<Float> getHFData(){
        List<Float> data = new ArrayList<>();
        
        return data;
    }
    @GetMapping(value = "/bitdata")
    public String getMethodName()
            throws ModbusProtocolException, ModbusNumberException, ModbusIOException {
        //return new SomeData();
        return ScadaImpl.getData();
       // return "hello";
    }
    
}