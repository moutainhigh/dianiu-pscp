package com.edianniu.pscp.cs.bean.firefightingequipment;

import java.io.Serializable;

/**
 * 消防设施详情
 * @author zhoujianjian
 * @date 2017年11月1日 下午9:29:29
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
