package com.edianniu.pscp.cs.bean.engineeringproject;

import java.io.Serializable;

/**
 * 项目详情
 * @author zhoujianjian
 * 2017年9月19日下午11:20:51
 */
public class DetailsReqData implements Serializable{
	private static final long serialVersionUID = 1L;
	//用户ID
	private Long uid;
	//项目编号
	private String projectNo;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	
	

}
