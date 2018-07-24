package com.lens.core;

import com.lens.util.LogUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* Socker服务端 */
public class GBServer {

    private static ServerSocket serverSocket;
    private static ExecutorService executorService;//线程池

    protected static void launch() {
        LogUtil.i("启动仿真主机");
        try {
            serverSocket = new ServerSocket(Config.port);
            executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * Config.poolSize);
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("端口异常,程序退出");
            System.exit(0);
        }

        while (true) {
            Socket socket;
            try {
                //启动并发线程
                //接收客户连接,只要客户进行了连接,就会触发accept();从而建立连接
                socket = serverSocket.accept();
                socket.setSoTimeout(Config.timeOut);
                executorService.execute(new MyHandler(socket));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
