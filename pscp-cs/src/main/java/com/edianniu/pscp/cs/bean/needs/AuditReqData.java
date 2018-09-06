package com.edianniu.pscp.cs.bean.needs;

import java.io.Serializable;

/**
 * 需求审核
 * @author zhoujianjian
 * 2017年9月22日下午2:15:41
 */
public class AuditReqData implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long uid;
	//需求ID
	private String orderId;
	//审核结果   -1.不通过     2.通过
	private Integer status;
	//失败原因
	private String failReason;
	//要屏蔽的字
	private String maskString;
	//要删除的附件fid  多个用逗号分隔
	private String removedImgs;
	
	public String getRemovedImgs() {
		return removedImgs;
	}
	public void setRemovedImgs(String removedImgs) {
		this.removedImgs = removedImgs;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
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
	
}
