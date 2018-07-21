package com.lens.core;

import com.lens.util.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// TODO 优化报文响应机制

public class XmlFiles {
    private Map<String, String> reps = new HashMap<>();
    private String filePath = "";

    public XmlFiles() {
    }

    public Map<String, String> getFileList() {
        LogUtil.i("初始化reps");
//        try {
//            filePath = new ConfigUtill("./config.ini").getValue("rspPath");
//            LogUtil.i("响应报文文件路径:" + filePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        File dir = new File(filePath);
        File dir = new File(Config.rspPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files.length == 0)
            return reps;
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getName();
            if (fileName.endsWith("xml")) { // 判断文件名是否以.xml结尾
                String strFileName = files[i].getAbsolutePath();
                getFile(strFileName);
            } else {
                continue;
            }
        }
        return reps;
    }

    private void getFile(String fileName) {
        File file = new File(fileName);
        Long fileLen = file.length();

        // 将计算出的报文长度前补0，凑成指定长度的报文头长度值，用于在发送响应前拼接到报文正文前。
        StringBuilder sbFileLen = new StringBuilder(fileLen.toString());
        for(int i=sbFileLen.length();i<6;i++){
            sbFileLen.insert(0, "0");
        }
        LogUtil.i("文件名是: " + fileName + " 文件字节数: " + sbFileLen.toString());

//        byte[] fileHeader = sbFileLen.toString().getBytes();
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
        reps.put(fileName.substring(fileName.lastIndexOf(File.separator) + 1, fileName.length() - 4), new String(filecontent));
    }

}
