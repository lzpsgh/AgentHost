package com.lens.core;

import com.lens.util.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    //    public static String channel ="channel11";
    public static int port = 666;
    public static String rspPath = "rspPath111";
    public static int autoHeaderLen = 1;
    public static int timeOut = 999;
    public static int delay = 111;
    public static int poolSize = 222;
    public static boolean debugMode;

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
    }


}
