package com.edianniu.pscp.cs.bean.equipment;

import java.io.Serializable;

/**
 * 配电房设备详情
 * @author zhoujianjian
 * 2017年9月29日下午9:45:22
 */
public class DetailsReqData implements Serializable {
    private static final long serialVersionUID = 1L;
    //用户ID
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
