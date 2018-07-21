package com.lens.core;

import com.lens.util.LogUtil;

import java.io.*;
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
//        msgHandler = new MsgHandler();
    }

    private PrintWriter getWriter(Socket socket) throws IOException {
        OutputStream socketOut = socket.getOutputStream();
        return new PrintWriter(socketOut, true);
    }

    private BufferedReader getReader(Socket socket) throws IOException {
        InputStream socketIn = socket.getInputStream();
        return new BufferedReader(new InputStreamReader(socketIn));
    }

    @Override
    public void run() {
        try {
            LogUtil.i("建立连接，" + socket.getInetAddress() + ":" + socket.getPort());
            BufferedReader br = getReader(socket);
            PrintWriter pw = getWriter(socket);
            CharBuffer cb = CharBuffer.allocate(1024 * 5);
            String req = "";
            String rsp = "";
            int len = br.read(cb);
            //TODO 索引长度不能写死6位
            req = new String(cb.array()).substring(6, len);
            LogUtil.i("请求报文req:" + req);
            //原始方案
            rsp = getXmlRep.getRep(req);

            StringBuilder sbFileLen6 = new StringBuilder(String.valueOf(rsp.getBytes().length));
            LogUtil.i(sbFileLen6+"");
            for(int i=sbFileLen6.length();i<6;i++){
                sbFileLen6.insert(0, "0");
            }
            String fileLen6 = sbFileLen6.toString();

            LogUtil.i("6位头长度: " + fileLen6);
            LogUtil.i("组装响应报文: \r\n" + fileLen6+rsp);

            pw.write(fileLen6+rsp);
            pw.flush();
            br.close();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}