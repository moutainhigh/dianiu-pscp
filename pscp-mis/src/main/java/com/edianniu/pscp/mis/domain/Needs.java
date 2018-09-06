package com.edianniu.pscp.mis.domain;

import java.util.Date;

/**
 * Needs
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月21日 下午12:31:58
 * @version V1.0
 */
public class Needs extends BaseDo {
	private static final long serialVersionUID = 1L;
	// 需求ID
	private Long id;
	// 公司ID
	private Long companyId;

	// 需求编号
	private String orderId;

	// 需求名称
	private String name;

	// 需求描述
	private String describe;

	// 发布截止时间
	private Date publishCutoffTime;

	// 工作结束时间
	private Date workingStartTime;

	// 工作开始时间
	private Date workingEndTime;

	// 联迪人
	private String contactPerson;

	// 联系方式
	private String contactNumber;

	// 联系地址
	private String contactAddr;

	// 状态
	// -3：已超时；-2：已取消；-1：审核不通过；
	// 0：审核中；1：响应中(审核通过)；
	// 2：报价中； 3：已报价； 4：已合作
	private int status;

	// 如果有多个配电房，逗号隔开
	private String distributionRoomIds;

	// 备注
	private String remark;

	// 审核时间
	private Date auditTime;

	// 审核失败原因
	private String failReason;

	public Long getId() {
		return id;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getName() {
		return name;
	}

	public String getDescribe() {
		return describe;
	}

	public Date getPublishCutoffTime() {
		return publishCutoffTime;
	}

	public Date getWorkingStartTime() {
		return workingStartTime;
	}

	public Date getWorkingEndTime() {
		return workingEndTime;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public String getContactAddr() {
		return contactAddr;
	}

	public int getStatus() {
		return status;
	}

	public String getDistributionRoomIds() {
		return distributionRoomIds;
	}

	public String getRemark() {
		return remark;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public void setPublishCutoffTime(Date publishCutoffTime) {
		this.publishCutoffTime = publishCutoffTime;
	}

	public void setWorkingStartTime(Date workingStartTime) {
		this.workingStartTime = workingStartTime;
	}

	public void setWorkingEndTime(Date workingEndTime) {
		this.workingEndTime = workingEndTime;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public void setContactAddr(String contactAddr) {
		this.contactAddr = contactAddr;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setDistributionRoomIds(String distributionRoomIds) {
		this.distributionRoomIds = distributionRoomIds;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

}
