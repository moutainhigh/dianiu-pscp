package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;

/**
 * 门户：智能监控>监控列表>电流平衡
 * @author zhoujianjian
 * @date 2017年12月16日 上午10:04:28
 */
public class CurrentBalanceReqData implements Serializable{

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
