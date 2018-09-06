package com.edianniu.pscp.cs.bean.safetyequipment;

import java.io.Serializable;

/**
 * 配电房安全用具详情
 * @author zhoujianjian
 * @date 2017年10月31日 下午9:56:46
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
