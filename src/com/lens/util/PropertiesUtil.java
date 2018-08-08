package com.lens.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

    /**
     * 获取Properties
     * @param filePath 首选配置文件路径
     * @param filePath2 候选配置文件路径
     * @return 加载配置文件后的 properties
     */
    public static Properties getProp(String filePath,String filePath2){
        Properties pps = new Properties();
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                LogUtil.wtf("首选路径未找到配置文件，尝试在候选路径下搜索");
                file = new File(filePath2);
            }
            if (!file.exists()) {
                LogUtil.wtf("未找到配置文件");
                System.exit(0);
            }
            pps.load(new FileInputStream(file));
            LogUtil.wtf("加载配置文件:" + file.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pps;
    }
}
