/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月22日 下午4:33:29 
 * @version V1.0
 */
package com.edianniu.pscp.message.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月22日 下午4:33:29 
 * @version V1.0
 */
public class MeterInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String buildingId;
	private String gateWayId;
	private String meterId;
	private String subTermCode;//分项编码 01A00 01B00 01C00 01D00 01E00
	private Integer type=0;//1netdau 2 其他
	private Date reportTime;
	private Map<String,Object> meterData=new HashMap<String,Object>();
	private String functions;
	public String getGateWayId() {
		return gateWayId;
	}
	public String getMeterId() {
		return meterId;
	}
	public void setGateWayId(String gateWayId) {
		this.gateWayId = gateWayId;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public Map<String, Object> getMeterData() {
		return meterData;
	}
	public void setMeterData(Map<String, Object> meterData) {
		this.meterData = meterData;
	}
	public String getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	public Date getReportTime() {
		return reportTime;
	}
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	public String getFunctions() {
		return functions;
	}
	public void setFunctions(String functions) {
		this.functions = functions;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getSubTermCode() {
		return subTermCode;
	}
	public void setSubTermCode(String subTermCode) {
		this.subTermCode = subTermCode;
	}
}
