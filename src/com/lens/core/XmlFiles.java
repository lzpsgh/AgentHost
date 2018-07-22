package com.lens.core;

import com.lens.util.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// todo 优化报文响应机制

public class XmlFiles {

    private static Map<String, String> reps = new HashMap<>();

    public static Map<String, String> getFileList() {
        LogUtil.i("初始化reps");
        // todo rsp初始化提前
        File dir = new File(Config.rspPath);
        // todo 目录不存在时自动创建
        if(!dir.exists()){
            LogUtil.e("rsp directory not found");
            System.exit(0);
        }
        LogUtil.i("rsp path: " + Config.rspPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files.length == 0){
            LogUtil.i("rsp file not found");
            System.exit(0);
        }
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getName();
            if(!fileName.endsWith("xml")){
                continue;
            }

            // 获取每个文件的key
            String filesMapKey = files[i].getName().split("\\.")[0];
            LogUtil.d("每个文件存到内存中的key: " + filesMapKey);

            // 获取每个文件的value
            Long fileLen = files[i].length();//文件总字节数
            byte[] filecontent = new byte[fileLen.intValue()];
            try {
                FileInputStream in = new FileInputStream(files[i]);
                in.read(filecontent);
                in.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //加载到reps中
//            String filesMapKey = fileName.substring(fileName.lastIndexOf(File.separator) + 1, fileName.length() - 4);
            reps.put(filesMapKey, new String(filecontent));
//            LogUtil.i(reps.size()+"");
        }

        return reps;
    }

    /*
     *
    Deprecated
    private void getFile(String fileName) {
        File file = new File(fileName);
        Long fileLen = file.length();//文件总字节数

        byte[] filecontent = new byte[fileLen.intValue()];

        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String txnId = fileName.substring(fileName.lastIndexOf(File.separator) + 1, fileName.length() - 4);
        reps.put(txnId, new String(filecontent));
    }
    */

}
