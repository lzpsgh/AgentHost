package com.lens.core;

import com.lens.util.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    //    public static String channel ="channel11";
    public static String reservesZeroStr = "000000000000000000000000000000";

    // 配置文件 config 相关
    public static int port;
    public static String rspPath;
    public static int timeOut;
    public static int delay;
    public static int poolSize;
    public static int reqLen;
    public static int rspLen;
    public static boolean debugMode;
    public static String matchCode;

    public static void init() {

        String cfgPath2 = "D:/Prog/Workspace/AgentHost/cfg/config.ini";
        String cfgPath = "./config.ini";

        Properties pps = new Properties();
        try {
            File file = new File(cfgPath);
            if (!file.exists()) {
                LogUtil.i("can not found config in the current path，now try to search in default path");
                file = new File(cfgPath2);
            }
            if (!file.exists()) {
                LogUtil.i("can not found config");
                throw new Exception();
            }
            pps.load(new FileInputStream(file));
            LogUtil.i("load config:" + file.getCanonicalPath());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        poolSize = Integer.parseInt(pps.getProperty("poolSize", "10").trim());
        port = Integer.parseInt(pps.getProperty("port", "23333").trim());
        timeOut = Integer.parseInt(pps.getProperty("timeOut", "60000").trim());
        delay = Integer.parseInt(pps.getProperty("delay", "0").trim());
        rspPath = pps.getProperty("rspPath","./rsp").trim();
        reqLen = Integer.parseInt(pps.getProperty("reqLen", "6").trim());
        rspLen = Integer.parseInt(pps.getProperty("rspLen", "6").trim());
        debugMode = Boolean.getBoolean(pps.getProperty("debugMode", "FALSE"));
        matchCode = pps.getProperty("matchCode","Message.Sys_Head.TRAN_CODE").trim();



    }


}
