package com.edianniu.pscp.message.service.dubbo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.message.bean.GateWayInfo;
import com.edianniu.pscp.message.bean.MeterInfo;
import com.edianniu.pscp.message.bean.MeterLogInfo;
import com.edianniu.pscp.message.bean.WarningInfo;
import com.edianniu.pscp.message.commons.Result;
import com.edianniu.pscp.message.meter.domain.Meter;
import com.edianniu.pscp.message.meter.service.MeterService;
import com.edianniu.pscp.message.service.MeterPushService;
import com.edianniu.pscp.message.service.dubbo.MeterLogInfoService;
@Service
@Repository("meterLogInfoService")
public class MeterLogInfoServiceImpl implements MeterLogInfoService {
    @Autowired
	private MeterPushService meterPushService;
    @Autowired
    private MeterService meterService;

	@Override
	public Result pushMeterLog(List<MeterLogInfo> meterLogs) {
		return meterPushService.pushMeterLog(meterLogs);
	}
	@Override
	public Result pushMeter(List<MeterInfo> meters) {
		return meterPushService.pushMeter(meters);
	}
	@Override
	public Result pushGateWay(GateWayInfo gateWayInfo) {	
		return meterPushService.push(gateWayInfo);
	}
	@Override
	public Result pushWarningInfo(List<WarningInfo> warnings) {
		return meterPushService.pushWarning(warnings);
	}
	@Override
	public MeterInfo getMeterInfo(String buildingId, String gateWayId,
			String meterId) {
		MeterInfo meterInfo=null;
		Meter meter=meterService.getMeter(buildingId, gateWayId, meterId, 1);
		if(meter!=null){
			meterInfo=new MeterInfo();
			meterInfo.setBuildingId(buildingId);
			meterInfo.setGateWayId(gateWayId);
			meterInfo.setMeterId(meterId);
			meterInfo.setReportTime(meter.getReportTime());
			meterInfo.setType(meter.getType());
			meterInfo.setSubTermCode(meter.getSubTermCode());
			meterInfo.getMeterData().put("address", meter.getAddress());
		}
		return meterInfo;
	}

}
