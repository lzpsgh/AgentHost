package com.lens.core;

import com.lens.util.LogUtil;
import com.lens.util.PropertiesUtil;

import java.io.File;
import java.util.Properties;

public class Config {

    // 直接截取其子串作为响应报文头左补零的字符串
    public static String reservesZeroStr = "000000000000000000000000000000";
    // 首选路径
    public static String cfgPath = "./config.ini";
    // 候选路径，调试用
    public static String cfgPath2 = "D:/Prog/Workspace/AgentHost/cfg/config.ini";
    // 配置文件 config 相关
    public static int port;
    public static String rspPath;
    public static boolean needWatch;
    public static int timeOut;
    public static int delay;
    public static int poolSize;
    public static int reqLen;
    public static int rspLen;
    public static boolean debugMode;
    public static int logLevel;
    public static String matchCode;

    public static void initCfg() {
        loadPara(PropertiesUtil.getProp(cfgPath, cfgPath2));
        loadRsp(Config.rspPath,Config.needWatch);
    }

    private static void loadPara(Properties pps) {
        poolSize = Integer.parseInt(pps.getProperty("poolSize", "10").trim());
        port = Integer.parseInt(pps.getProperty("port", "23333").trim());
        timeOut = Integer.parseInt(pps.getProperty("timeOut", "60000").trim());
        delay = Integer.parseInt(pps.getProperty("delay", "0").trim());
        rspPath = pps.getProperty("rspPath", "./rsp").trim();
        needWatch = pps.getProperty("needWatch", "1").equals("1") ? Boolean.TRUE : Boolean.FALSE;
        reqLen = Integer.parseInt(pps.getProperty("reqLen", "6").trim());
        rspLen = Integer.parseInt(pps.getProperty("rspLen", "6").trim());
        debugMode = pps.getProperty("debugMode", "0").equals("1") ? Boolean.TRUE : Boolean.FALSE;
        logLevel = Integer.parseInt(pps.getProperty("logLevel", "2").trim());
        matchCode = pps.getProperty("matchCode", "Message.Sys_Head.TRAN_CODE").trim();
    }

    private static void loadRsp(String filePath, Boolean needWatch) {
        File file = new File(filePath);
        if (!(file.exists() && file.isDirectory())) {
            LogUtil.e("响应报文目录未找到");
            System.exit(0);
        }
        if (needWatch) {
            WatchServiceHandler watchServiceHandler = new WatchServiceHandler();
            watchServiceHandler.watch(filePath);
            LogUtil.i("注册目录事件监听器："+filePath);
            //absWatchServiceHandler.watch(rspPath2);
        }
        LogUtil.i("加载响应报文");
        // TODO 预加载
    }

}
