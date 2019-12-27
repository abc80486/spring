package com.neo.web;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;
import com.neo.mapper.ServicesCtrlMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class ScadaImpl {
    @Autowired
    private static ServicesCtrlMapper servicesCtrlMapper;

    private static final String ADDR = "127.0.0.1";
    //private static final String ADDR = "172.16.72.78";
    private static final int SLAVEID = 1;

    static ModbusMaster master = null;
    static {
        try {
            TcpParameters tcpParameters = new TcpParameters();

            // 设置TCP的ip地址
            InetAddress adress = InetAddress.getByName(ADDR);

            // TCP参数设置ip地址
            // tcpParameters.setHost(InetAddress.getLocalHost());
            tcpParameters.setHost(adress);

            // TCP设置长连接
            tcpParameters.setKeepAlive(true);
            // TCP设置端口，这里设置是默认端口502
            tcpParameters.setPort(Modbus.TCP_PORT);
            // tcpParameters.s
            // 创建一个主机
            master = ModbusMasterFactory.createModbusMasterTCP(tcpParameters);
            Modbus.setAutoIncrementTransactionId(true);

        } catch (Exception e) {
            System.out.println("connect error!");
        }
    }
    // @Override
   // public static  int quantity = 10;
   public static boolean[] coil01;
   public static boolean[] coil02;
   public static int[] register03;
   public static int[] register04;
   public static List<Object> data = new ArrayList<>();
    //static long timewp1= new Date().getTime();
    @Scheduled(cron = "* * * * * ?")
    public static boolean getData() throws ModbusProtocolException, ModbusNumberException, ModbusIOException {
          int quantity = 100;
          coil01 = master.readCoils(SLAVEID, 0, quantity);
          coil02 = master.readDiscreteInputs(SLAVEID, 0, quantity);
          register03 = master.readHoldingRegisters(SLAVEID, 0, quantity);
          register04 = master.readInputRegisters(SLAVEID, 0, quantity);

          //List<Object> data = new ArrayList<>();
          data.clear();
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
          data.add(new Date());
          return true;
    }
    
    public static void printdata(int quantity){
        System.out.printf("%8s %8s %8s %8s %8s\n","address" , "01" ,  "02"  ," 03",  " 04").toString();
        System.out.printf("----------------------------------------------\n").toString();
        for(int i=0;i<quantity;i++){
            // System.out.print();
            System.out.printf("%8d %8b %8b %8d %8d\n",i,coil01[i],coil02[i],register03[i],register04[i]);
        }
    }
    private static long waterPumpOpTime = 1l;
    public static List<String> log = new ArrayList<>();
    public static boolean setRegister(int addr,int col) {
        Date now = new Date();
        try {
            if(now.getTime()-waterPumpOpTime < 100l){
                log.add(now.toString()+"不可频繁操作，请稍后再试！");
                System.out.println(now.toString()+"不可频繁操作，请稍后再试！");
                return false;
            }
            else{
                //master.writeSingleCoil(SLAVEID, addr, col);
                if(register03[addr]!=col)
                  master.writeSingleRegister(SLAVEID, addr,(int)col);
                else return false;
                waterPumpOpTime = now.getTime();
                log.add(now.toString() + " 地址" + addr + " = " + (int)col + " 设置成功");
                System.out.println(now.toString() + " 地址" + addr + " = " + (int)col + " 设置成功");
                return true;
            }
        } catch (Exception e) {
            log.add(now.toString() + " 地址" + addr + "  设置失败");
            System.out.println(now.toString() + " 地址" + addr + "  设置失败");
            return false;
        }
    }

    public static void setCoil(int addr,boolean col) {
            try {
                Date now = new Date();
                if(now.getTime()-waterPumpOpTime < 100l){
                    log.add("不可频繁操作，请稍后再试！");
                    System.out.println("不可频繁操作，请稍后再试！");
                }
                else{
                    if(coil01[addr]!=col) master.writeSingleCoil(SLAVEID, addr, col);
                    else return;
                    waterPumpOpTime = now.getTime();
                    if(col == true){
                        log.add(now.toString() + " 地址" + addr + " 启动成功");
                        System.out.println(now.toString() + " 地址" + addr + "  启动成功");
                    }
                    else{
                        log.add(now.toString() + " 地址" + addr + "  关闭成功");
                        System.out.println(now.toString() + " 地址" + addr + "  关闭成功");
                    }
                }
            } catch (ModbusProtocolException e) {
                e.printStackTrace();
            } catch (ModbusNumberException e) {
                e.printStackTrace();
            } catch (ModbusIOException e) {
                e.printStackTrace();
            }
    }
        //return true;
}