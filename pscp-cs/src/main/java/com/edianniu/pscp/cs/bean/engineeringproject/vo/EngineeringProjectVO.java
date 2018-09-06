package com.edianniu.pscp.cs.bean.engineeringproject.vo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.edianniu.pscp.cs.bean.engineeringproject.ActualPriceInfo;
import com.edianniu.pscp.cs.bean.needs.Attachment;

/**
 * 
 * @author zhoujianjian 2017年9月19日下午11:03:50
 */
public class EngineeringProjectVO implements Serializable{
	private static final long serialVersionUID = 1L;

	// $column.comments
	private Long id;
	// 项目编号
	private String projectNo;
	// $column.comments
	private Long customerId;
	// $column.comments
	private String name;
	// $column.comments
	private String description;
	// $column.comments
	private String startTime;
	// $column.comments
	private String endTime;
	// $column.comments
	private Integer sceneInvestigation;
	// $column.comments
	private Long companyId;
	// $column.comments
	private String contactNumber;
	// $column.comments
	private String contactPerson;
	// $column.comments
	private String contactAddr;
	// 公司名称
	private String companyName;
	// 项目状态 0：进行中； 1：费用待确认 2：支付失败 3：已结算
	private Integer status;
	// 需求ID
	private Long needsId;
	// $column.comments
	private String showTime;
	// $column.comments
	private String createUser;
	// 实际结算金额
	@JSONField(serialize = false)
	private String actualSettlementAmount;
	// 实际结算金额及其附件
	private ActualPriceInfo actualPriceInfo;
	//合作附件
	private List<Attachment> cooperationInfo;
	
	private String roomIds;
	
	public String getRoomIds() {
		return roomIds;
	}

	public void setRoomIds(String roomIds) {
		this.roomIds = roomIds;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactAddr() {
		return contactAddr;
	}

	public void setContactAddr(String contactAddr) {
		this.contactAddr = contactAddr;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Integer getSceneInvestigation() {
		return sceneInvestigation;
	}

	public void setSceneInvestigation(Integer sceneInvestigation) {
		this.sceneInvestigation = sceneInvestigation;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getNeedsId() {
		return needsId;
	}

	public void setNeedsId(Long needsId) {
		this.needsId = needsId;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public ActualPriceInfo getActualPriceInfo() {
		return actualPriceInfo;
	}

	public void setActualPriceInfo(ActualPriceInfo actualPriceInfo) {
		this.actualPriceInfo = actualPriceInfo;
	}

	public List<Attachment> getCooperationInfo() {
		return cooperationInfo;
	}

	public void setCooperationInfo(List<Attachment> cooperationInfo) {
		this.cooperationInfo = cooperationInfo;
	}

	public String getActualSettlementAmount() {
		return actualSettlementAmount;
	}

	public void setActualSettlementAmount(String actualSettlementAmount) {
		this.actualSettlementAmount = actualSettlementAmount;
	}

}
