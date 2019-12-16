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

public class ScadaImpl {
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

    public static boolean getData() throws ModbusProtocolException, ModbusNumberException, ModbusIOException {
         //String out="";
         Map<String,Integer> data = new HashMap<>();
         int quantity = 100;
          coil01 = master.readCoils(SLAVEID, 0, quantity);
          coil02 = master.readDiscreteInputs(SLAVEID, 0, quantity);
          register03 = master.readHoldingRegisters(SLAVEID, 0, quantity);
          register04 = master.readInputRegisters(SLAVEID, 0, quantity);
          printdata(coil01, coil02, register03, register04, quantity);
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
         List<Boolean> k = new ArrayList<>();
         //k = (Collection)coil01;
             k.add(coil01[4]);
             k.add(coil01[1]);
             k.add(coil01[0]);

             k.add(coil01[85]);
             k.add(coil01[7]);
             k.add(coil01[6]);

             k.add(coil01[74]);
             k.add(coil01[71]);
             k.add(coil01[70]);

             k.add(coil01[81]);
             k.add(coil01[78]);
             k.add(coil01[79]);
             k.add(coil01[77]);

             k.add(coil01[28]);
             k.add(coil01[25]);
             k.add(coil01[26]);
             k.add(coil01[24]);
            
         List<Integer> p = new ArrayList<>();
            p.add(register03[3]);
            p.add(register03[4]);
            p.add(register03[5]);
            p.add(register03[6]);
            p.add(register03[16]);
            p.add(register03[17]);

            p.add(register03[15]);
            p.add(register03[14]);
            p.add(register03[11]);
            p.add(register03[13]);
            p.add(register03[12]);
            p.add(register03[10]);
         return true;
         
    }
    public static void printdata(boolean[] coil01,boolean[] coil02,int[] register03,int[] register04,int quantity){
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
                master.writeSingleRegister(SLAVEID, addr,(int)col);
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
                    master.writeSingleCoil(SLAVEID, addr, col);
                    //master.writeSingleRegister(SLAVEID, addr, data);
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
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ModbusNumberException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ModbusIOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
        //return true;
}