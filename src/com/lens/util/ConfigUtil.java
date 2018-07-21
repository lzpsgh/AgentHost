package com.lens.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

/**
 * @author xingshaocheng_at_gmail.com ("_at_" to "@")
 * @version 1.0 2008-12-7
 */

public class ConfigUtil {

    private final Hashtable properties = new Hashtable();

    private final Vector groups = new Vector();

    private final Hashtable values = new Hashtable();


    public ConfigUtil(String filePath) throws IOException {
        FileReader fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);
        String s = null;
        int lineNum = 0;
        Properties newComProperties = null;
        boolean newComKey = false;
        // parse each line
        while (null != (s = br.readLine())) {
            lineNum++;
            String tmp = s.trim();
            if (null == tmp)
                continue;
            if (0 == tmp.length())
                continue;
            char firstChar = tmp.charAt(0);
            if ('#' == firstChar)
                continue;
            else if (';' == firstChar)
                continue;
            // remove comments
            // int endStr = Math.max(tmp.indexOf("#"), tmp.indexOf(";"));
            int endStr = Math.max(tmp.indexOf("#"), tmp.indexOf("#"));
            if (-1 != endStr) {
                tmp = tmp.substring(0, endStr);
            }
            if ('[' == firstChar) {
                if (']' != tmp.charAt(tmp.length() - 1)) {
                    throw new IOException();
                } else {
                    String comKey = s.substring(1, tmp.length() - 1).trim();
                    this.groups.add(comKey);
                    newComProperties = new Properties();
                    this.properties.put(comKey, newComProperties);
                    newComKey = true;
                }
            } else {
                String[] keyAndValue = tmp.split("=");
                if ("" == keyAndValue[0])
                    throw new IOException();
                else {
                    String key = keyAndValue[0].trim();
                    String value = "";
                    for (int i = 1; i < keyAndValue.length; i++) {
                        if (i == 1)
                            value += keyAndValue[i].trim();
                        else
                            value += "=" + keyAndValue[i].trim();
                    }
                    if (newComKey)
                        newComProperties.put(key, value);
                    else
                        this.properties.put(key, value);
                    this.values.put(key, value);
                }
            }
        }
    }

    /**
     * Get the property by the "key", if not unique, return the first
     *
     * @param key
     * @return value
     */
    public synchronized String getValue(String key) {
        return (String) this.values.get(key);
    }

    /**
     * Get the property in the "Group"
     *
     * @param groupKey
     * @param key
     * @return value
     */
    public synchronized String getValue(String groupKey, String key) {
        Properties myProps = (Properties) this.properties.get(groupKey);
        if (null != myProps) {
            return myProps.getProperty(key);
        }
        return null;
    }

    public Properties getPropertiesByGroupKey(String groupKey) {
        return (Properties) this.properties.get(groupKey);
    }

    /**
     * for debug
     */
    public String toString() {

        StringBuffer sb = new StringBuffer();

        Enumeration mixedKey = this.properties.keys();

        while (mixedKey.hasMoreElements())

        {

            String key = (String) mixedKey.nextElement();

            Object tmpObj = this.properties.get(key);

            if (tmpObj instanceof String)

            {

                sb.append((String) key + "=" + tmpObj + System.getProperty("line.separator"));

            } else if (tmpObj instanceof Properties)

            {

                Properties theGrpProps = ((Properties) tmpObj);

                Enumeration vkeys = theGrpProps.keys();

                sb.append("----Group Key:" + key + " BEGIN----" + System.getProperty("line.separator"));

                while (vkeys.hasMoreElements()) {
                    String theKey = (String) vkeys.nextElement();
                    sb.append(theKey + "=" + theGrpProps.getProperty(theKey) + System.getProperty("line.separator"));
                }
                sb.append("----Group Key:" + key + " END------" + System.getProperty("line.separator"));
            }
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        // 注意不同OS下的分隔符
        // String fileUrl= System.getProperty("user.dir") + "/config/agent.ini";
        String fileUrl = "./conf/agent.ini";
        try {
            ConfigUtil conf = new ConfigUtil(fileUrl);
            System.out.println("port=" + conf.getValue("SERVER", "timeOut"));
            System.out.println("xmlpath=" + conf.getValue("XMLFILE", "xmlpath"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
