package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;

/**
 * 分户：峰谷利用
 * @author zhoujianjian
 * @date 2017年12月16日 下午3:20:16
 */
public class UseFengGUReqData implements Serializable{

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
