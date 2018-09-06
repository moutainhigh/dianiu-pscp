package com.edianniu.pscp.mis.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用来输出公司ETL格式的日志文件，供数据仓库导入
 */
public class DWLogger {
    private Logger logger = LoggerFactory.getLogger(DWLogger.class);;

    public DWLogger() {
   }

    public void log(Object... args) {
        StringBuilder builder = new StringBuilder();
        for (Object arg : args) {
            String value = String.valueOf(arg);
            value = escape(value);
            builder.append(value).append(",");
        }
        builder.deleteCharAt(builder.lastIndexOf(","));
        logger.info(builder.toString());
    }

    public String escape(String val) {
        if(val==null){
            return "";
        }
        return val.replace(",", "\\,");
    }
}
