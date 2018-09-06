package com.edianniu.pscp.search.common;

/**
 * ClassName: Constants
 *
 * @author: tandingbo
 * CreateTime: 2017-10-11 10:50
 */
public class Constants {
    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final int ERROR_CODE = 500;
    public static final int BAD_REQUEST = 400;
    public static final int EXIST_CODE = 304;

    public static final String NEEDS_INDEX = "needs";
    public static final String NEEDS_TYPE = "market";
    public static final String METER_INDEX = "meter";
   
    public static final String METER_DAY_LOG_TYPE   = "meter_day_log";//日日志
    
    public static final String METER_DAY_DETAIL_LOG_TYPE   = "meter_day_detail_log";//日明细仪表日志数据
    
    public static final String METER_MONTH_LOG_TYPE="meter_month_log";//月日志
    
    public static final String METER_LOG_TYPE   = "meter_log";//仪表实时数据
   
    public static final String METER_DAY_LOAD_DETAIL_TYPE = "meter_day_load_detail_log";//日负荷明细数据
    public static final String METER_DEMAND_DETAIL_TYPE   = "meter_demand_detail_log";//需量明细
    public static final String METER_DAY_VOLTAGE_CURRENT_DETAIL_TYPE   = "meter_day_voltage_current_detail_log";//电压电流明细
    
    

}
