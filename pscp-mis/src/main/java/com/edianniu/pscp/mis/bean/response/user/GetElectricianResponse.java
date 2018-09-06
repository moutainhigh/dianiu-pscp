package com.edianniu.pscp.mis.bean.response.user;

import com.edianniu.pscp.mis.bean.electrician.ElectricianInfo;
import com.edianniu.pscp.mis.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;
@JSONMessage(messageCode = 2002009)
public class GetElectricianResponse extends BaseResponse{

	private ElectricianInfo electricianInfo;

	public ElectricianInfo getElectricianInfo() {
		return electricianInfo;
	}

	public void setElectricianInfo(ElectricianInfo electricianInfo) {
		this.electricianInfo = electricianInfo;
	}

	
	
	
}
