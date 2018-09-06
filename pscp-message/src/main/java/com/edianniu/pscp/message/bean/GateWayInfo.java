/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月22日 下午4:35:59 
 * @version V1.0
 */
package com.edianniu.pscp.message.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 采集设备网关
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月22日 下午4:35:59 
 * @version V1.0
 */
public class GateWayInfo implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer type;//设备类型//1:netdau,2:其他
    private String gatewayId;//设备ID
    private String buildingId;
    private Integer status;//1 online,0 offline
    private Date reportTime;
    private Map<String,Object> gateWayData=new HashMap<String,Object>();
	public Integer getType() {
		return type;
	}
	public String getGatewayId() {
		return gatewayId;
	}
	public Map<String, Object> getGateWayData() {
		return gateWayData;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}
	public void setGateWayData(Map<String, Object> gateWayData) {
		this.gateWayData = gateWayData;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getReportTime() {
		return reportTime;
	}
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	public String getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
}
