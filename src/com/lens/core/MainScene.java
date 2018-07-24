package com.lens.core;

public class MainScene {
    public static void main(String[] args) {
        try{
            Config.initCfg(); // 加载配置文件
            GBServer.launch(); // 启动仿真主机
            //GBClient.launch(); // 启动仿真终端
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}