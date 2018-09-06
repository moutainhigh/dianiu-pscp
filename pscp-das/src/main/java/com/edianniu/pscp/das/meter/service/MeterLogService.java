package com.edianniu.pscp.das.meter.service;

import com.edianniu.pscp.message.bean.MeterLogInfo;
import com.edianniu.pscp.search.support.meter.DayDetailLogReqData;
import com.edianniu.pscp.search.support.meter.DayLoadDetailReqData;
import com.edianniu.pscp.search.support.meter.DayLogReqData;
import com.edianniu.pscp.search.support.meter.DayVoltageCurrentDetailReqData;
import com.edianniu.pscp.search.support.meter.DemandDetailReqData;
import com.edianniu.pscp.search.support.meter.MeterLogReqData;
import com.edianniu.pscp.search.support.meter.MonthLogReqData;
import com.edianniu.pscp.search.support.meter.OpResult;

/**
 * 仪表日志服务
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月27日 下午9:08:36 
 * @version V1.0
 */
public interface MeterLogService {
  /**
   * 保存仪表日志到数据库
   * @param meterLog
   */
  public void saveMeterLogToDb(MeterLogInfo meterLog);
   /**
    * 保存仪表实时日志
    * @param meterLogReqData
    */
   public OpResult saveMeterLog(MeterLogReqData meterLogReqData);
   /**
    * 保存需量明细
    * @param demandDetailReqData
    */
   public void saveDemandDetail(DemandDetailReqData demandDetailReqData);
   /**
    * 保存日负荷明细数据
    * @param dayLoadDetailReqData
    */
   public void saveDayLoadDetail(DayLoadDetailReqData dayLoadDetailReqData);
   /**
    * 保存日电压电量明细数据
    * @param dayVoltageCurrentDetailReqData
    */
   public void saveDayVoltageCurrentDetail(DayVoltageCurrentDetailReqData dayVoltageCurrentDetailReqData);
   /**
    * 保存月电费数据
    * @param monthLogReqData
    */
   public void saveMonthLog(MonthLogReqData monthLogReqData);
   /**
    * 保存日明细日志数据
    * @param dayDetailLogReqData
    */
   public void saveDayDetailLog(DayDetailLogReqData dayDetailLogReqData);
   /**
    * 保存日日志数据
    * @param dayLogReqData
    */
   public void saveDayLog(DayLogReqData dayLogReqData);
}
