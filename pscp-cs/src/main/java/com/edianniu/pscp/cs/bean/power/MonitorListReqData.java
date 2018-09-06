package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;

/**
 * 智能监控>监控列表
 * @author zhoujianjian
 * @date 2017年12月12日 下午2:20:08
 */
public class MonitorListReqData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long uid;
	
	private Integer offset;
	
	private Integer limit;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}
