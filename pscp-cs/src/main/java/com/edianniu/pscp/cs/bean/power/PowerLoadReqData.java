package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;

/**
 * 用电负荷
 * @author zhoujianjian
 * @date 2017年12月7日 下午7:07:37
 */
public class PowerLoadReqData implements Serializable{

	private static final long serialVersionUID = 1L;
	//客户uid
	private Long uid;
	
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

}
