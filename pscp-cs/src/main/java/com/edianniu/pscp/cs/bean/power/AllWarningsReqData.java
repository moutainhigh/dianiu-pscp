package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;

/**
 * 门户--智能监控>告警
 * 获取服务商所有客户的告警列表
 * @author zhoujianjian
 * @date 2017年12月14日 下午3:05:33
 */
public class AllWarningsReqData implements Serializable{

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
