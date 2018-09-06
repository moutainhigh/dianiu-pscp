package com.edianniu.pscp.cs.bean.operationrecord;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 配电房客户操作记录新增与编辑
 * @author zhoujianjian
 * @date 2017年10月18日 下午5:16:34
 */
public class SaveReqData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long uid;
	// 记录ID（新增时为空，编辑时不为空）
	private Long id;
	
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
	
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

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

	public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
