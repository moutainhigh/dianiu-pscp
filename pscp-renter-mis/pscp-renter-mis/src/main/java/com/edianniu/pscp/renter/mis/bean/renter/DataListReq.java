package com.edianniu.pscp.renter.mis.bean.renter;

import java.io.Serializable;

import com.edianniu.pscp.renter.mis.commons.Constants;

/**
 * 租客数据详情
 * @author zhoujianjian
 * @date 2018年4月8日 下午2:48:35
 */
public class DataListReq implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long uid;
	
	// 按年、月、日查询时格式：4位、6位、8位
    //（1）yyyy ：“2017”
    //（2）yyyyMM：“201701”（不支持“20171”）
    //（3）yyyyMMdd：“20170101”（不支持“201781”）
	// (为空时，默认按月查询)
	private String time;
	
	private Integer offset;
	
	private Integer limit = Constants.DEFAULT_PAGE_SIZE;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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
