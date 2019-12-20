package com.neo;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.neo.mapper.ServicesCtrlMapper;
import com.neo.web.ScadaImpl;
import com.neo.web.ServicesCtrlController;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.neo.mapper")
public class MybatisAnnotationApplication {
	public static void main(String[] args) throws ModbusProtocolException, ModbusNumberException, ModbusIOException {
		SpringApplication.run(MybatisAnnotationApplication.class, args);
		ScadaImpl.getData();
		//ServicesCtrlController a = new ServicesCtrlController();
		//a.test2();
	}
	private static void init(){
		
	}
}
