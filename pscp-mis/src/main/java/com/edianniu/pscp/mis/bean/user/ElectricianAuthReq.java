package com.edianniu.pscp.mis.bean.user;

import java.io.Serializable;

import com.edianniu.pscp.mis.bean.electrician.ElectricianInfo;

public class ElectricianAuthReq  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long uid;
	
	private ElectricianInfo info;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public ElectricianInfo getInfo() {
		return info;
	}

	public void setInfo(ElectricianInfo info) {
		this.info = info;
	}
	
	

}
