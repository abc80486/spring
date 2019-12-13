package com.neo.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestPages {
    @PostMapping("/coil_ctrl")
    public boolean setCoil(int addr,boolean status){
        ScadaImpl.setCoil(addr, status);
        return status;
    }
    @PostMapping("/register_ctrl")
    public boolean setRegister(int addr,)
}