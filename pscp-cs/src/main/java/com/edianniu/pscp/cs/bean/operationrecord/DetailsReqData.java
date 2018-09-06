package com.edianniu.pscp.cs.bean.operationrecord;

import java.io.Serializable;

/**
 * 配电房操作记录详情
 * @author zhoujianjian
 * @date 2017年10月29日 下午10:07:14
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
