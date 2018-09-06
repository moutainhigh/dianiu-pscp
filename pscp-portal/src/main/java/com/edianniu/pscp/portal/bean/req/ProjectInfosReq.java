package com.edianniu.pscp.portal.bean.req;

import java.io.Serializable;

/**
 * 客户--->我的项目  
 * @author zhoujianjian
 * @date 2018年4月27日 下午6:13:37
 */
public class ProjectInfosReq implements Serializable{

	private static final long serialVersionUID = 1L;

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
}
