package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;

/**
 * 门户：智能监控>监控列表>电压健康
 * @author zhoujianjian
 * @date 2017年12月15日 下午4:23:32
 */
public class SafetyVoltageReqData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long uid;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	
}
