/**
 * @author yanlin.chen  yanlin.chen@edianniu.com
 * @version V1.0 2018年4月27日 下午8:21:03 
 */
package com.edianniu.pscp.mis.bean.log;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年4月27日 下午8:21:03 
 * @version V1.0
 */
public class MeterVO implements Serializable{
	private static final long serialVersionUID = 1L;
	String buildingNo;
	String gatewayNo;
	String meterNo;
	String address;
	public String getBuildingNo() {
		return buildingNo;
	}
	public String getGatewayNo() {
		return gatewayNo;
	}
	public String getMeterNo() {
		return meterNo;
	}
	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}
	public void setGatewayNo(String gatewayNo) {
		this.gatewayNo = gatewayNo;
	}
	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
