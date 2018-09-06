package com.edianniu.pscp.das.meter.service;

import com.edianniu.pscp.das.meter.bean.MeterLogDo;


/**
 * MeterLogAnalysisService
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月27日 下午9:08:36 
 * @version V1.0
 */
public interface MeterLogAnalysisService {
   /**
    * 日志分析
    * @param meterLogDo
    * @return
    */
   public boolean doAnalysis(MeterLogDo meterLogDo) throws Exception; 
}
