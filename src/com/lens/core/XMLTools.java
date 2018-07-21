package com.lens.core;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Iterator;

public class XMLTools {

    public static String readStringXml(String xml, String iKey) {
        Document doc = null;
        String nKey = "";
        String value = "";
        try {
            // 下面的是通过解析xml字符串的
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            Element rootElt = doc.getRootElement(); // 获取根节点
            nKey = rootElt.getName();// 拿到根节点的名称
            Iterator iter = rootElt.elementIterator(); // 获取根节点下的子节点
            //遍历根节点
            while (iter.hasNext()) {
                value = getNode(iter, nKey, iKey);
                if (value.length() > 0)
                    return value;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return value;
    }

    private static String getNode(Iterator iter, String nKey, String iKey) {
        Element recordEle = (Element) iter.next();
        String key = recordEle.getName();
        String value = recordEle.getTextTrim();
        nKey = nKey + "." + key;
        if (nKey.equals(iKey)){
            return value;
        }
        Iterator iter0 = recordEle.elementIterator();
        while (iter0.hasNext()) {
            value = getNode(iter0, nKey, iKey);
            if (value.length() > 0)
                return value;
        }
        return "";
    }

}
