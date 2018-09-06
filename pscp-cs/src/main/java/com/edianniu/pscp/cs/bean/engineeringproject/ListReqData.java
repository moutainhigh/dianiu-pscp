package com.edianniu.pscp.cs.bean.engineeringproject;

import java.io.Serializable;

import com.edianniu.pscp.cs.commons.Constants;

/**
 * 项目列表
 * @author zhoujianjian
 * 2017年9月19日下午2:33:26
 */
public class ListReqData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long uid;
	private Integer offset;
	private Integer pageSize = Constants.DEFAULT_PAGE_SIZE;
	private String status;   //主状态："progressing"：进行中       "finished"：已结束
	
	//项目编号
 	private String projectNo;
	//项目名称
	private String name;
	//服务商公司
	private String companyName;
	// 工作时间 yyyy-MM-dd
	private String workTime;
	// 提交时间  yyyy-MM-dd
	private String createTime;
	// 子状态
	private String subStatus;
	
	public String getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	
	
	
	
}
