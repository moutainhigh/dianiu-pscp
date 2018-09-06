package com.edianniu.pscp.message.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.message.bean.GateWayInfo;
import com.edianniu.pscp.message.bean.MeterInfo;
import com.edianniu.pscp.message.bean.MeterLogInfo;
import com.edianniu.pscp.message.bean.WarningInfo;
import com.edianniu.pscp.message.commons.DefaultResult;
import com.edianniu.pscp.message.commons.Result;
import com.edianniu.pscp.message.kafka.producer.DefaultKafkaProducerService;
import com.edianniu.pscp.message.service.MeterPushService;

@Service
@Repository("meterPushService")
public class DefaultMeterPushService implements MeterPushService {
    @Autowired
    private DefaultKafkaProducerService kafkaProducerService;
	@Override
	public Result pushMeterLog(List<MeterLogInfo> meterLogs) {
		for(MeterLogInfo meterLog:meterLogs){
			kafkaProducerService.sendMeterLog(meterLog.getMeterId(), JSON.toJSONString(meterLog));
		}
		return new DefaultResult();
	}
	@Override
	public Result pushMeter(List<MeterInfo> meters) {
		for(MeterInfo meter:meters){
			kafkaProducerService.sendMeterInfo("METER#"+meter.getBuildingId()+meter.getGateWayId()+meter.getMeterId(), JSON.toJSONString(meter));
		}
		return new DefaultResult();
	}
	@Override
	public Result push(GateWayInfo gateWayInfo) {
		kafkaProducerService.sendMeterInfo("GATEWAY#"+gateWayInfo.getBuildingId()+gateWayInfo.getGatewayId(), JSON.toJSONString(gateWayInfo));
		return new DefaultResult();
	}
	
	@Override
	public Result pushWarning(List<WarningInfo> warnings){
		for (WarningInfo warning : warnings) {
			kafkaProducerService.sendWarningInfo("WARNING#"+warning.getMeterId()+warning.getWarningType(), JSON.toJSONString(warning));
		}
		return new DefaultResult();
	}

}
