package com.lens.util;

import com.lens.core.Config;

public class LogUtil {

    static String className;
    static String methodName;
    static int lineNumber;

    private static void getRef(StackTraceElement[] stElements) {
        className = stElements[1].getFileName();
        methodName = stElements[1].getMethodName();
        lineNumber = stElements[1].getLineNumber();
    }

    public static void wtf(String message){
        if (Config.debugMode) {
            getRef(new Throwable().getStackTrace());
            System.out.println(" 【" + className + "】【" + methodName + ":" + lineNumber + "】 " + message);
        } else {
            System.out.println(message);
        }
    }
    public static void e(String message) {
        if (Config.logLevel >= 1) {
            if (Config.debugMode) {
                getRef(new Throwable().getStackTrace());
                System.out.println(" 【" + className + "】【" + methodName + ":" + lineNumber + "】 " + message);
            } else {
                System.out.println(message);
            }
        }
    }

    public static void i(String message) {
        if (Config.logLevel >= 2) {
            if (Config.debugMode) {
                getRef(new Throwable().getStackTrace());
                System.out.println(" 【" + className + "】【" + methodName + ":" + lineNumber + "】 " + message);
            } else {
                System.out.println(message);
            }
        }
    }

    public static void d(String message) {
        if (Config.logLevel >= 3) {
            if (Config.debugMode) {
                getRef(new Throwable().getStackTrace());
                System.out.println(" 【" + className + "】【" + methodName + ":" + lineNumber + "】 " + message);
            } else {
                System.out.println(message);
            }
        }
    }

}
