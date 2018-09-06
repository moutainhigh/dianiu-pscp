package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;

/**
 * 警报详情
 * @author zhoujianjian
 * @date 2017年12月11日 下午5:34:52
 */
public class WarningDetailReqData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long uid;
	
	private Long id;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
