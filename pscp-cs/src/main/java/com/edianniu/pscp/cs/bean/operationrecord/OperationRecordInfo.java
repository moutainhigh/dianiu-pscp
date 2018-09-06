package com.edianniu.pscp.cs.bean.operationrecord;

import java.io.Serializable;
import java.util.Date;

/**
 * 配电房客户操作记录
 * @author zhoujianjian
 * @date 2017年10月18日 下午5:15:43
 */
public class OperationRecordInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long roomId;
	
	private Long companyId;
	
	private String recordNum;
	
	private String equipmentTask;
	
	private String groundLeadNum;
	
	private Date startTime;
	
	private Date endTime;
	
	private String issuingCommand;
	
	private String receiveCommand;
	
	private String operator;
	
	private String guardian;
	
	private String remark;
	
	private Date createTime;
	
	private Date modifiedTime;
	
	private String createUser;
	
	private String modifiedUser;
	
	private Integer deleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(String recordNum) {
		this.recordNum = recordNum;
	}

	public String getEquipmentTask() {
		return equipmentTask;
	}

	public void setEquipmentTask(String equipmentTask) {
		this.equipmentTask = equipmentTask;
	}

	public String getGroundLeadNum() {
		return groundLeadNum;
	}

	public void setGroundLeadNum(String groundLeadNum) {
		this.groundLeadNum = groundLeadNum;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getIssuingCommand() {
		return issuingCommand;
	}

	public void setIssuingCommand(String issuingCommand) {
		this.issuingCommand = issuingCommand;
	}

	public String getReceiveCommand() {
		return receiveCommand;
	}

	public void setReceiveCommand(String receiveCommand) {
		this.receiveCommand = receiveCommand;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getGuardian() {
		return guardian;
	}

	public void setGuardian(String guardian) {
		this.guardian = guardian;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
}
