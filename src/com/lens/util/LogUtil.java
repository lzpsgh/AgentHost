package com.lens.util;

import com.lens.core.Config;

public class LogUtil {

    static String className;
    static String methodName;
    static int lineNumber;
    static final int LEVEL = 2;
    static boolean debugMode = Config.debugMode;

    private LogUtil() {
    }

    private static void getRef(StackTraceElement[] stElements) {
        className = stElements[1].getFileName();
        methodName = stElements[1].getMethodName();
        lineNumber = stElements[1].getLineNumber();
    }

    public static void e(String message) {
        if (LEVEL >= 1) {
            getRef(new Throwable().getStackTrace());
            if (debugMode) {
                System.out.println(" 【" + className + "】【" + methodName + ":" + lineNumber + "】 " + message);
            } else {
                System.out.println(message);
            }
        }
    }

    public static void i(String message) {
        if (LEVEL >= 2) {
            getRef(new Throwable().getStackTrace());
            if (debugMode) {
                System.out.println(" 【" + className + "】【" + methodName + ":" + lineNumber + "】 " + message);
            } else {
                System.out.println(message);
            }
        }
    }

    public static void d(String message) {
        if (LEVEL >= 3) {
            getRef(new Throwable().getStackTrace());
            if (debugMode) {
                System.out.println(" 【" + className + "】【" + methodName + ":" + lineNumber + "】 " + message);
            } else {
                System.out.println(message);
            }
        }
    }

}
