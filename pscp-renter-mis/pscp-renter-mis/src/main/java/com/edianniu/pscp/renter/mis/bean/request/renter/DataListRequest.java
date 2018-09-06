package com.edianniu.pscp.renter.mis.bean.request.renter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.renter.mis.bean.request.TerminalRequest;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 租客用电数据列表
 * @author zhoujianjian
 * @date 2018年4月8日 下午2:02:34
 */
@JSONMessage(messageCode = 1002302)
public class DataListRequest extends TerminalRequest{

	// 按年、月、日查询时格式：4位、6位、8位
    //（1）yyyy ：“2017”
    //（2）yyyyMM：“201701”（不支持“20171”）
    //（3）yyyyMMdd：“20170101”（不支持“201781”）
	// (为空时，默认按月查询)
	private String time;
	
	private Integer offset;

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
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
