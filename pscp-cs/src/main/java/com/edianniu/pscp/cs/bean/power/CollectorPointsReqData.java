package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;

/**
 * 分页获取个监测点数据
 * @author zhoujianjian
 * @date 2017年12月16日 下午6:49:00
 */
public class CollectorPointsReqData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long uid;
	
	private Integer offset;
	
	private Integer limit;
	// 查询时间起始 HH:mm:ss
	private String startTime;
	// 查询时间结束 HH:mm:ss
	private String endTime;
	
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	

}
