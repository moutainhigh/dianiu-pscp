package com.edianniu.pscp.cs.bean.operationrecord.vo;

import java.io.Serializable;

public class OperationRecordVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String recordNum;
	
	private String roomName;
	
	private Long roomId;
	
	private String equipmentTask;
	
	private String groundLeadNum;
	
	private String startTime;
	
	private String endTime;
	
	private String issuingCommand;
	
	private String receiveCommand;
	
	private String operator;
	
	private String guardian;
	
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEquipmentTask() {
		return equipmentTask;
	}

	public void setEquipmentTask(String equipmentTask) {
		this.equipmentTask = equipmentTask;
	}

	public String getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(String recordNum) {
		this.recordNum = recordNum;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
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

	public String getGroundLeadNum() {
		return groundLeadNum;
	}

	public void setGroundLeadNum(String groundLeadNum) {
		this.groundLeadNum = groundLeadNum;
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

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	

}
