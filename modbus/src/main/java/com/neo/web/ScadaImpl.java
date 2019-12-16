package com.neo.web;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;

public class ScadaImpl {
    //private static final String ADDR = "127.0.0.1";
    private static final String ADDR = "172.16.72.78";
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
    public static String getData() throws ModbusProtocolException, ModbusNumberException, ModbusIOException {
         String out="";
         int quantity = 100;
         boolean[] coil01 = master.readCoils(SLAVEID, 0, quantity);
         boolean[] coil02 = master.readDiscreteInputs(SLAVEID, 0, quantity);
         int[] register03 = (master.readHoldingRegisters(SLAVEID, 0, quantity));
         int[] register04 = master.readInputRegisters(SLAVEID, 0, quantity);
         out += System.out.printf("%8s %8s %8s %8s %8s\n","address" , "01" ,  "02"  ," 03",  " 04").toString();
         out += System.out.printf("----------------------------------------------\n").toString();
         
         for(int i=0;i<quantity;i++){
            // System.out.print();
             out += System.out.printf("%8d %8b %8b %8d %8d\n",i+1,coil01[i],coil02[i],register03[i],register04[i]);
         }
         out = out.toString();
         System.out.println(out);
         return out;
         

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