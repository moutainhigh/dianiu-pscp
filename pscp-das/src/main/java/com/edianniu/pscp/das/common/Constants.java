package com.edianniu.pscp.das.common;

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
    public static final String REPORT_INDEX = "report";
    
    public static final String REPORT_DAY_AGGREGATE_TYPE   = "report_day_aggregate";//日综合数据
    public static final String REPORT_DAY_LOAD_DETAIL_TYPE = "report_day_load_detail";//日负荷明细数据
    public static final String REPORT_MONTH_ELECTRIC_TYPE="report_month_electric";//月用电量数据
    public static final String REPORT_DAY_ELECTRIC_TYPE="report_day_electric";//日用电量数据
    public static final String REPORT_MONTH_ELECTRIC_CHARGE_TYPE="report_month_electric_charge";//月用电费数据electric charge
    public static final String REPORT_METER_LOG_TYPE   = "report_meter_log";//仪表实时数据
    public static final String REPORT_DAY_ELECTRIC_CHARGE_TYPE   = "report_day_electric_charge";//日用电情况
    public static final String REPORT_DAY_ELECTRIC_CHARGE_DETAIL_TYPE   = "report_day_electric_charge_detail";//日用电明细情况
    public static final String REPORT_DAY_POWER_FACTOR_DETAIL_TYPE   = "report_day_power_factor_detail";//日功率因数明细
    public static final String REPORT_DAY_POWER_FACTOR_TYPE   = "report_day_power_factor";//日功率因数
    public static final String REPORT_MONTH_POWER_FACTOR_TYPE   = "report_month_power_factor";//月功率因数
    public static final String REPORT_DEMAND_DETAIL_TYPE   = "report_demand_detail";//需量明细
    public static final String REPORT_DAY_VOLTAGE_CURRENT_DETAIL_TYPE   = "report_day_voltage_current_detail";//电压电流明细
    
    public static final int U_STATUS_NORMAL=0;//正常
    public static final int U_STATUS_HEIGHT=1;//1过高
    public static final int U_STATUS_LOWER=-1;//-1偏低
    public static final int U_STATUS_ERROR=-2;//-2异常
    
    public static final int TAG_YES = 1;
    public static final int TAG_NO = 0;

}
