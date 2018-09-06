package com.edianniu.pscp.message.service;

import java.util.List;

import com.edianniu.pscp.message.bean.GateWayInfo;
import com.edianniu.pscp.message.bean.MeterInfo;
import com.edianniu.pscp.message.bean.MeterLogInfo;
import com.edianniu.pscp.message.bean.WarningInfo;
import com.edianniu.pscp.message.commons.Result;

/**
 * 
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月25日 下午5:44:53 
 * @version V1.0
 */
public interface MeterPushService {
	/**
	 * 推送仪表日志信息
	 * @param meterLogs
	 * @return
	 */
	public Result pushMeterLog(List<MeterLogInfo> meterLogs);
	/**
	 *  推送仪表信息
	 * @param meters
	 * @return
	 */
	public Result pushMeter(List<MeterInfo> meters);
	/**
	 * 推送设备网关信息
	 * @param gateWayInfo
	 * @return
	 */
	public Result push(GateWayInfo gateWayInfo);
	
	/**
	 * 推送告警信息
	 * @param warnings
	 * @return
	 */
	public Result pushWarning(List<WarningInfo> warnings);
}
