package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;

/**
 * 实时负荷
 * @author zhoujianjian
 * @date 2017年12月12日 下午9:07:25
 */
public class RealTimeLoadReqData implements Serializable{

	private static final long serialVersionUID = 1L;
	// 客户ID
	private Long uid;
	// 查询日期
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
