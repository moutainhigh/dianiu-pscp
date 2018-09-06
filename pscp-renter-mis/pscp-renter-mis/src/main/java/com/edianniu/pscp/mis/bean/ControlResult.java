package com.edianniu.pscp.mis.bean;

import java.io.Serializable;
/**
 * 
 * @author yanlin.chen  yanlin.chen@edianniu.com
 * @version V1.0 2018年4月23日 下午6:15:19 
 */
public class ControlResult implements Serializable{
	private static final long serialVersionUID = 1L;
	private String meterId;
	/**
	 *  F0 -> 正常
	 *  F1 -> 没找到当前meterId 仪表
	 *  F2 -> 当前仪表对应的串口参数没有配置
	 *  F3 -> 仪表返回超时  
	 */
	private String code;//
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
