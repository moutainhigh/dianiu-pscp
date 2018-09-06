package com.edianniu.pscp.renter.mis.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * ClassName: PropertiesUtil
 * Author: tandingbo
 * CreateTime: 2017-05-04 15:36
 */
public class PropertiesUtil {
    private Properties props;

    public PropertiesUtil(String fileName) {
        readProperties(fileName);
    }

    private void readProperties(String fileName) {
        InputStream ins;
        props = new Properties();
        try {
            ins = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
            props.load(ins);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取某个属性
     */
    public String getProperty(String key) {
        return props.getProperty(key);
    }

    public static void main(String[] args) {
        PropertiesUtil config = new PropertiesUtil("app.properties");
        String contactNumber = config.getProperty("contact.number");
        System.out.println(contactNumber);
    }
}
