package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;

/**
 * 用电量
 * @author zhoujianjian
 * @date 2017年12月7日 下午8:11:23
 */
public class PowerQuantityReqData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long uid;
	
	// yyyy-MM
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
