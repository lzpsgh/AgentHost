package com.lens.core;

import com.lens.util.LogUtil;

import java.util.Map;

public class GetXmlRep {
    private static Map<String, String> reps = null;
    private static long delay = Config.delay;

    public GetXmlRep() {
        if (reps == null) {
            // 可以改成静态调用
            reps = XmlFiles.getFileList();
        }
    }

    public String getRep(String req) {
        String rep ;
        // TODO 双匹配码支持
        String key = "Message.Sys_Head.TRAN_CODE";
        String txnid = XMLTools.readStringXml(req, key);
        rep = reps.get(txnid);
        try {
            Thread.sleep(delay);
            LogUtil.i("模拟超时:" + delay +"毫秒");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return rep;
    }

}
