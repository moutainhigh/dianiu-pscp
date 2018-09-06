package com.edianniu.pscp.cs.bean.dutylog;

import java.io.Serializable;

/**
 * 配电房值班日志详情
 * @author zhoujianjian
 * @date 2017年10月30日 下午7:04:43
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
