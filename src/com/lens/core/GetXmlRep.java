package com.lens.core;

import com.lens.util.LogUtil;

import java.util.Map;

public class GetXmlRep {
    private static Map<String, String> reps = null;

    public GetXmlRep() {
        if (reps == null) {
            // 可以改成静态调用
            reps = XmlFiles.getFileList();
        }
    }

    public String getRep(String req) {
        String rep ;
        // todo 双匹配码支持
        String key = "Message.Sys_Head.TRAN_CODE";
        String txnid = XMLTools.readStringXml(req, key);
        rep = reps.get(txnid);
        if(Config.delay>0){
            try {
                Thread.sleep(Config.delay);
                LogUtil.i("模拟超时:" + Config.delay +"毫秒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return rep;
    }

}
