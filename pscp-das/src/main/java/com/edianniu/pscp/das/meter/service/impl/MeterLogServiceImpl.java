package com.edianniu.pscp.das.meter.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.das.common.Constants;
import com.edianniu.pscp.das.meter.dao.MeterLogDao;
import com.edianniu.pscp.das.meter.domain.MeterLog;
import com.edianniu.pscp.das.meter.log.DWLogger;
import com.edianniu.pscp.das.meter.service.MeterLogService;
import com.edianniu.pscp.message.bean.MeterLogInfo;
import com.edianniu.pscp.search.dubbo.ReportProduceDubboService;
import com.edianniu.pscp.search.support.meter.DayDetailLogReqData;
import com.edianniu.pscp.search.support.meter.DayLoadDetailReqData;
import com.edianniu.pscp.search.support.meter.DayLogReqData;
import com.edianniu.pscp.search.support.meter.DayVoltageCurrentDetailReqData;
import com.edianniu.pscp.search.support.meter.DemandDetailReqData;
import com.edianniu.pscp.search.support.meter.MeterLogReqData;
import com.edianniu.pscp.search.support.meter.MonthLogReqData;
import com.edianniu.pscp.search.support.meter.OpResult;
/**
 * MeterLogServiceImpl
 * 1)数据保存到es
 * 2)es保存失败时
 * 2.1）打印失败日志
 * 2.2）失败数据写入文件供后续处理
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月27日 下午9:14:01 
 * @version V1.0
 */
@Service
@Repository("meterLogService")
public class MeterLogServiceImpl implements MeterLogService {
	private static final Logger logger = LoggerFactory
			.getLogger(MeterLogServiceImpl.class);
	private static final DWLogger dwLogger=new DWLogger();
	@Autowired
	private MeterLogDao meterLogDao;
	@Autowired
	private ReportProduceDubboService reportProduceDubboService;
	@Override
	public OpResult saveMeterLog(MeterLogReqData meterLogReqData) {
		OpResult result=reportProduceDubboService.save(meterLogReqData);
		if(!result.isSuccess()){
			logger.error("meterLog error:{}",JSON.toJSONString(result));
			if(result.getResultCode()==Constants.ERROR_CODE||result.getResultCode()==Constants.BAD_REQUEST){
				dwLogger.log("[meterLog]",JSON.toJSONString(meterLogReqData));
			}
		}
		return result;
	}
	@Override
	public void saveDayLoadDetail(DayLoadDetailReqData dayLoadDetailReqData) {
		OpResult result=reportProduceDubboService.save(dayLoadDetailReqData);
		if(!result.isSuccess()){
			dwLogger.log("[dayLoadDetail]",JSON.toJSONString(dayLoadDetailReqData));
			logger.error("dayLoadDetail error:{}",JSON.toJSONString(result));
		}		
	}
	@Override
	public void saveMonthLog(MonthLogReqData monthLogReqData){
		OpResult result=reportProduceDubboService.save(monthLogReqData);
		if(!result.isSuccess()){
			dwLogger.log("[monthElectricCharge]",JSON.toJSONString(monthLogReqData));
			logger.error("monthElectricCharge error:{}",JSON.toJSONString(result));
		}	
	}
	@Override
	public void saveDayDetailLog(
			DayDetailLogReqData dayDetailLogReqData) {
		OpResult result=reportProduceDubboService.save(dayDetailLogReqData);
		if(!result.isSuccess()){
			dwLogger.log("[dayDetailLog]",JSON.toJSONString(dayDetailLogReqData));
			logger.error("dayDetailLog error:{}",JSON.toJSONString(result));
		}	
		
	}
	@Override
	public void saveDayLog(
			DayLogReqData dayLogReqData) {
		OpResult result=reportProduceDubboService.save(dayLogReqData);
		if(!result.isSuccess()){
			dwLogger.log("[dayLog]",JSON.toJSONString(dayLogReqData));
			logger.error("dayLog error:{}",JSON.toJSONString(result));
		}	
		
	}
	@Override
	public void saveDemandDetail(DemandDetailReqData demandDetailReqData) {
		OpResult result=reportProduceDubboService.save(demandDetailReqData);
		if(!result.isSuccess()){
			dwLogger.log("[demandDetail]",JSON.toJSONString(demandDetailReqData));
			logger.error("demandDetail error:{}",result);
		}	
		
	}
	@Override
	public void saveDayVoltageCurrentDetail(
			DayVoltageCurrentDetailReqData dayVoltageCurrentDetailReqData) {
		OpResult result=reportProduceDubboService.save(dayVoltageCurrentDetailReqData);
		if(!result.isSuccess()){
			dwLogger.log("[dayVoltageCurrentDetail]",JSON.toJSONString(dayVoltageCurrentDetailReqData));
			logger.error("dayVoltageCurrentDetail error:{}",result);
		}	
	}
	@Override
	@Transactional
	public void saveMeterLogToDb(MeterLogInfo meterLog) {
		//重复校验
		MeterLog log=new MeterLog();
		log.setReportTime(meterLog.getReportTime());
		log.setType(meterLog.getType());
		log.setSubTermCode(meterLog.getSubTermCode());
		log.setData(JSON.toJSONString(meterLog.getData()));
		log.setMeterId(meterLog.getMeterId());
		meterLogDao.save(log);
	}
	
	
	
   
}
