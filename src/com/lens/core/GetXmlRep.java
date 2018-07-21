package com.lens.core;

import com.lens.util.ConfigUtil;
import com.lens.util.LogUtil;

import java.io.IOException;
import java.util.Map;

public class GetXmlRep {
    private static Map<String, String> reps = null;
    private static long sleepTime = 0;

    public GetXmlRep() {
        if (reps == null) {
            reps = new XmlFiles().getFileList();
            try {
//                sleepTime = new Long(new ConfigUtil("D:/Busi/AgentHost/config.ini").getValue("sleepTime"));
                sleepTime = new Long(new ConfigUtil("./config.ini").getValue("sleepTime"));
            } catch (NumberFormatException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getRep(String req) {
        String rep = "";
        String key = "Message.Sys_Head.TRAN_CODE";
        String txnid = XMLTools.readStringXml(req, key);
        rep = reps.get(txnid);
        try {
            Thread.sleep(sleepTime);
            LogUtil.i("模拟超时:" + sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return rep;
    }

}
