package com.lens.core;

import com.lens.util.ConfigUtil;
import com.lens.util.LogUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* Socker服务端 */
public class GBServer {

    public String configPath;

    private int poolSize = 1;//单个CPU线程池大小
    private int port = 8821;
    private int timeOut = 19999 * 1000;
    private ServerSocket serverSocket;
    private ExecutorService executorService;//线程池

    public GBServer() {
        //加载并读取根目录下的配置文件 config.ini
        loadCfg();
    }

    private void loadCfg() {
        configPath = "./config.ini";
//        configPath = "D:/Busi/AgentHost/config.ini";
        loadCfg(configPath);
    }

    private void loadCfg(String configPath) {
        LogUtil.i("开始加载配置文件: " + configPath);
        try {
            poolSize = Integer.parseInt(new ConfigUtil(configPath).getValue("poolSize"));
            port = Integer.parseInt(new ConfigUtil(configPath).getValue("port"));
            timeOut = Integer.parseInt(new ConfigUtil(configPath).getValue("timeOut"));
            serverSocket = new ServerSocket(port);
            executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * poolSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        //启动并发线程
        LogUtil.i("开始启动仿真主机");
        while (true) {
            Socket socket;
            try {
                //接收客户连接,只要客户进行了连接,就会触发accept();从而建立连接
                socket = serverSocket.accept();
                socket.setSoTimeout(timeOut);
                executorService.execute(new MyHandler(socket));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}


