package com.edianniu.pscp.message.service.dubbo;

import java.util.List;

import com.edianniu.pscp.message.bean.GateWayInfo;
import com.edianniu.pscp.message.bean.MeterInfo;
import com.edianniu.pscp.message.bean.MeterLogInfo;
import com.edianniu.pscp.message.bean.WarningInfo;
import com.edianniu.pscp.message.commons.Result;

/**
 * 仪表日志信息dubbo服务
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月22日 下午3:02:52 
 * @version V1.0
 */
public interface MeterLogInfoService {
	
	/**
	 * 推送仪表日志信息
	 * @param meterLogs
	 * @return
	 */
	public Result pushMeterLog(List<MeterLogInfo> meterLogs);
	/**
	 * 推送仪表信息
	 * @param meters
	 * @return
	 */
	public Result pushMeter(List<MeterInfo> meters);
	/**
	 * 获取仪表信息
	 * @return
	 */
	public MeterInfo getMeterInfo(String buildingNo,String gateWayId,String meterId);
	/**
	 * 推送设备网关信息
	 * @param gateWayInfo
	 * @return
	 */
	public Result pushGateWay(GateWayInfo gateWayInfo);
	
	/**
	 * 推送告警信息
	 * @param warnings
	 * @return
	 */
	public Result pushWarningInfo(List<WarningInfo> warnings);
}
