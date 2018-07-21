package com.lens.core;

import com.lens.util.LogUtil;

import java.util.Map;

public class GetXmlRep {
    private static Map<String, String> reps = null;
    private static long sleepTime = Config.sleepTime;

    public GetXmlRep() {
        if (reps == null) {
            reps = new XmlFiles().getFileList();
        }
    }

    public String getRep(String req) {
        String rep ;
        // TODO 双匹配码支持
        String key = "Message.Sys_Head.TRAN_CODE";
        String txnid = XMLTools.readStringXml(req, key);
        rep = reps.get(txnid);
        try {
            Thread.sleep(sleepTime);
            LogUtil.i("模拟超时:" + sleepTime +"秒");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return rep;
    }

}
