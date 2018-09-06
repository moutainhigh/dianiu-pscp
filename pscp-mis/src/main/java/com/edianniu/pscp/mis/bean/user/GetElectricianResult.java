package com.edianniu.pscp.mis.bean.user;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.electrician.ElectricianInfo;

public class GetElectricianResult extends Result{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ElectricianInfo  info;
	public ElectricianInfo getInfo() {
		return info;
	}
	public void setInfo(ElectricianInfo info) {
		this.info = info;
	}
	

	

}
