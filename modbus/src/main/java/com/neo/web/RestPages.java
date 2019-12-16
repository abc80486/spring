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
    @GetMapping("/getData")
    public List<Object> getHFData() throws ModbusProtocolException, ModbusNumberException, ModbusIOException {
        ScadaImpl.getData();
        List<Object> data = new ArrayList<>();
        data.add(ScadaImpl.coil01[4]);
        data.add(ScadaImpl.coil01[1]);
        data.add(ScadaImpl.coil01[0]);

        data.add(ScadaImpl.coil01[85]);
        data.add(ScadaImpl.coil01[7]);
        data.add(ScadaImpl.coil01[6]);

        data.add(ScadaImpl.coil01[74]);
        data.add(ScadaImpl.coil01[71]);
        data.add(ScadaImpl.coil01[70]);

        data.add(ScadaImpl.coil01[81]);
        data.add(ScadaImpl.coil01[78]);
        data.add(ScadaImpl.coil01[79]);
        data.add(ScadaImpl.coil01[77]);

        data.add(ScadaImpl.coil01[28]);
        data.add(ScadaImpl.coil01[25]);
        data.add(ScadaImpl.coil01[26]);
        data.add(ScadaImpl.coil01[24]);

        data.add(ScadaImpl.register03[3]);
        data.add(ScadaImpl.register03[4]);
        data.add(ScadaImpl.register03[5]);
        data.add(ScadaImpl.register03[6]);
        data.add(ScadaImpl.register03[16]);
        data.add(ScadaImpl.register03[17]);

        data.add(ScadaImpl.register03[15]);
        data.add(ScadaImpl.register03[14]);
        data.add(ScadaImpl.register03[11]);
        data.add(ScadaImpl.register03[13]);
        data.add(ScadaImpl.register03[12]);
        data.add(ScadaImpl.register03[10]);
        return data;
    }
    
}