package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;

/**
 * 功率因数
 * @author zhoujianjian
 * @date 2017年12月8日 下午3:04:52
 */
public class PowerFactorReqData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long uid;
	
	private Long lineId;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getLineId() {
		return lineId;
	}

	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}


}
