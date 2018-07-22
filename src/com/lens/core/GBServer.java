package com.lens.core;

import com.lens.util.LogUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* Socker服务端 */
public class GBServer {

    private ServerSocket serverSocket;
    private ExecutorService executorService;//线程池

    public GBServer() {
        try{
            // 加载并读取根目录下的配置文件 config.ini
            initCfg();
            // 监听端口和初始化线程池
            initSocketAndTPool();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initCfg() {
        Config.init();
    }

    private void initSocketAndTPool() throws IOException{
        serverSocket = new ServerSocket(Config.port);
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * Config.poolSize);
    }

    protected void launch() {

        LogUtil.i("GBserver launch");
        while (true) {
            Socket socket;
            //启动并发线程
            try {
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


