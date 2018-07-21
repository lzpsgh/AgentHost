package com.lens.core;

import com.lens.util.LogUtil;

public class MainScene {
    public static void main(String[] args) {
        try{
            LogUtil.i("AgentHost start");
            new GBServer().launch();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}