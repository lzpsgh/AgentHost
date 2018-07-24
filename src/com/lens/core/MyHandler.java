package com.lens.core;

import com.lens.util.LogUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.CharBuffer;

public class MyHandler implements Runnable {
    private Socket socket;
    private GetXmlRep getXmlRep;
    //    private MsgHandler msgHandler;
    private long sleepTime;

    public MyHandler(Socket socket) {
        this.socket = socket;
        getXmlRep = new GetXmlRep();
    }

    @Override
    public void run() {
        BufferedReader br = null;
        PrintWriter pw = null;
        LogUtil.i("建立连接，" + socket.getInetAddress() + ":" + socket.getPort());
        try {

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream(), true);
            CharBuffer cb = CharBuffer.allocate(1024 * 5);
            String req = "";
            String rsp = "";
            int len = br.read(cb);
            if (len <= 0) {
                LogUtil.e("接收报文长度小于0");
                throw new Exception();
            }
            req = new String(cb.array()).substring(Config.reqLen, len);
            LogUtil.i("请求报文:\r\n" + req + "\r\n");
            //原始方案
            rsp = getXmlRep.getRep(req);
            // 将计算出的报文长度前补0，凑成指定长度的报文头长度值，用于在发送响应前拼接到报文正文前。
            String actualRspLen = String.valueOf(rsp.getBytes().length); // "768"
            if (Config.rspLen < actualRspLen.length()) {
                LogUtil.e("实际报文长度大于 rspLen 参数");
                throw new Exception();
            }
            String plusRspLen = Config.reservesZeroStr.substring(0, Config.rspLen - actualRspLen.length()); // "000"
            String totalRsp = plusRspLen + actualRspLen + rsp;
            LogUtil.i("响应报文:\r\n" + totalRsp);
            pw.write(totalRsp);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                pw.close();
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}