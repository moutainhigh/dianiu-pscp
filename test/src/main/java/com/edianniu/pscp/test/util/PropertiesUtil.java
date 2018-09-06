package com.edianniu.pscp.test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * ClassName: PropertiesUtil
 * Author: tandingbo
 * CreateTime: 2017-05-04 15:36
 */
public class PropertiesUtil {
    private Properties props;
    public PropertiesUtil(String filePath) {
        readProperties(filePath);
    }

    private void readProperties(String filePath) {
        InputStream ins;
        props = new Properties();
        try {
            ins = new FileInputStream(new File(filePath));
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
        PropertiesUtil config = new PropertiesUtil("D://work//web-work//test//src//main//resources//app.properties");
        String contactNumber = config.getProperty("access.ip");
        System.out.println(contactNumber);
    }
}
