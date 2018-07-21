package com.lens.core;

/*
改成每次交易时才找响应报文.xml，免得来回重启。该选项可配
同时改成自动获取报文长度，该选项可配
*/

public class MainScene {
    public static void main(String[] args) {
        new GBServer().start();
    }
}