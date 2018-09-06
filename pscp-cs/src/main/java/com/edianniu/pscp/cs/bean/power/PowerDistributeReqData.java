package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;

/**
 * 电量分布
 * @author zhoujianjian
 * @date 2017年12月8日 上午10:47:47
 */
public class PowerDistributeReqData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long uid;

	private String date;

	public Long getUid() {
		return uid;
	}
	
	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
