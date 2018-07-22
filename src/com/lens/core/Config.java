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
    public static boolean debugMode;
    public static int reqLen;
    public static int rspLen;

    public static void init() {

        String cfgPath2 = "D:/Busi/AgentHost/config.ini";
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

        rspPath = pps.getProperty("rspPath");
        port = Integer.parseInt(pps.getProperty("port", "23333").trim());
        timeOut = Integer.parseInt(pps.getProperty("timeOut", "60000").trim());
        delay = Integer.parseInt(pps.getProperty("delay", "0").trim());
        poolSize = Integer.parseInt(pps.getProperty("poolSize", "10").trim());
        debugMode = Boolean.getBoolean(pps.getProperty("debugMode", "FALSE"));
        reqLen = Integer.parseInt(pps.getProperty("reqLen", "6").trim());
        rspLen = Integer.parseInt(pps.getProperty("rspLen", "6").trim());


    }


}
