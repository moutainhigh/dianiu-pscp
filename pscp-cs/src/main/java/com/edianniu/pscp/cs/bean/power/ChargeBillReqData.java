package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;

/**
 * 电费单
 * @author zhoujianjian
 * @date 2017年12月8日 下午5:01:13
 */
public class ChargeBillReqData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long uid;
	// yyyy-MM
	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}
	
}
