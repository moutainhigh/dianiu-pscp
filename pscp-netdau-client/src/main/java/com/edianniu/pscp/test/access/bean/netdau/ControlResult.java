package com.edianniu.pscp.test.access.bean.netdau;

import java.io.Serializable;
/**
 * 
 * @author yanlin.chen  yanlin.chen@edianniu.com
 * @version V1.0 2018年4月23日 下午6:15:19 
 */
public class ControlResult implements Serializable{
	private static final long serialVersionUID = 1L;
	private String meterId;
	private String code;
	public String getMeterId() {
		return meterId;
	}
	public String getCode() {
		return code;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public void setCode(String code) {
		this.code = code;
	}

}
