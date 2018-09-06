package com.edianniu.pscp.cs.bean.firefightingequipment;

import java.io.Serializable;

/**
 * 消防设施删除
 * @author zhoujianjian
 * @date 2017年11月1日 下午10:16:36
 */
public class DeleteReqData implements Serializable{

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
