package com.edianniu.pscp.cs.domain;

import java.util.Date;

/**
 * 客户设备巡检日志
 * @author zhoujianjian
 * @date 2017年11月1日 下午10:36:05
 */
public class InspectingLog extends BaseDo {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long equipmentId;
	
	private Integer type;
	
	private Long companyId;
	
	private Integer status;
	
	private String content;
	
	private Date fixTestTime;
	
	private Date actualTestTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getFixTestTime() {
		return fixTestTime;
	}

	public void setFixTestTime(Date fixTestTime) {
		this.fixTestTime = fixTestTime;
	}

	public Date getActualTestTime() {
		return actualTestTime;
	}

	public void setActualTestTime(Date actualTestTime) {
		this.actualTestTime = actualTestTime;
	}
	
}
