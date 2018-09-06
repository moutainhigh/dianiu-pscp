package com.edianniu.pscp.cs.bean.request.needs;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 需求审核
 * @author zhoujianjian
 * 2017年9月22日下午1:07:29
 */
@JSONMessage(messageCode = 1002156)
public class AuditRequest extends TerminalRequest{
	
	//需求编号
	private String orderId;
	//审核结果   -1.不通过     1.通过
	private Integer status;
	//失败原因
	private String failReason;
	//要屏蔽的字
	private String maskString;
	//要屏蔽的附件fid  多个用逗号分隔
	private String removedImgs;

	public String getRemovedImgs() {
		return removedImgs;
	}

	public void setRemovedImgs(String removedImgs) {
		this.removedImgs = removedImgs;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getMaskString() {
		return maskString;
	}

	public void setMaskString(String maskString) {
		this.maskString = maskString;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.DEFAULT_STYLE);
	}
}
