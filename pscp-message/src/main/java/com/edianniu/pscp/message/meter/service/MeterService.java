package com.edianniu.pscp.message.meter.service;

import com.edianniu.pscp.message.bean.GateWayInfo;
import com.edianniu.pscp.message.bean.MeterInfo;
import com.edianniu.pscp.message.bean.WarningInfo;
import com.edianniu.pscp.message.meter.domain.CompanyMeter;
import com.edianniu.pscp.message.meter.domain.GateWay;
import com.edianniu.pscp.message.meter.domain.Meter;
/**
 * 仪表服务
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月27日 下午4:38:47 
 * @version V1.0
 */
public interface MeterService {
	public CompanyMeter getCompanyMeterById(String meterId);
	public void handleGateWay(GateWayInfo gateWayInfo);
	public void handleMeter(MeterInfo meterInfo);
	public Meter getMeterById(Long id);
	public Meter getMeter(String buildingId,String gatewayId,String meterId,Integer type);
	public int saveMeter(Meter meter);
	public int updateMeter(Meter meter);
	public int updateMeterStatus(Meter meter);
	
	public GateWay getGateWayById(Long id);
	public GateWay getGateWay(String buildingId,
			String gatewayId);
	public int saveGateWay(GateWay gateWay);
	public int updateGateWay(GateWay gateWay);
	public int updateGateWayStatus(GateWay gateWay);
	public void handleWarning(WarningInfo warningInfo);
}
