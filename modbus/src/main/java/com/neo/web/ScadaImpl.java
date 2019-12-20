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

public class ScadaImpl {
    @Autowired
    private static ServicesCtrlMapper servicesCtrlMapper;

    private static final String ADDR = "127.0.0.1";
    //private static final String ADDR = "172.16.72.78";
    private static final int SLAVEID = 1;

    // bit table
    // bit table

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
    //static int main_data=-1;
    public static boolean getData() throws ModbusProtocolException, ModbusNumberException, ModbusIOException {
          int quantity = 100;
          coil01 = master.readCoils(SLAVEID, 0, quantity);
          coil02 = master.readDiscreteInputs(SLAVEID, 0, quantity);
          register03 = master.readHoldingRegisters(SLAVEID, 0, quantity);
          register04 = master.readInputRegisters(SLAVEID, 0, quantity);
          //servicesCtrlMapper.insertStatusData(-1l);
          //printdata(10);
        /*
         data.put("wp1_ctrl", coil01[5-1]!=false?1:0);//
         data.put("wp1_status", coil01[2-1]!=false?1:0);//
         data.put("wp1_mode", coil01[1-1]!=false?1:0);//

         data.put("wp2_ctrl", coil01[86-1]!=false?1:0);//
         data.put("wp2_status", coil01[8-1]!=false?1:0);//
         data.put("wp2_mode", coil01[7-1]!=false?1:0);//

         data.put("wp3_ctrl", coil01[75-1]!=false?1:0);//
         data.put("wp3_status", coil01[72-1]!=false?1:0);//
         data.put("wp3_mode", coil01[71-1]!=false?1:0);//

         data.put("valve1_ctrl", coil01[82-1]!=false?1:0);//
         data.put("valve1_open_status", coil01[79-1]!=false?1:0);//
         data.put("valve1_close_status", coil01[80-1]!=false?1:0);//
         data.put("valve1_mode", coil01[78-1]!=false?1:0);//

         data.put("valve2_ctrl", coil01[29-1]!=false?1:0);//
         data.put("valve2_open_status", coil01[26-1]!=false?1:0);//
         data.put("valve2_close_status", coil01[27-1]!=false?1:0);//
         data.put("valve2_mode", coil01[25-1]!=false?1:0);//
        */
         //data.put(key, value)
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