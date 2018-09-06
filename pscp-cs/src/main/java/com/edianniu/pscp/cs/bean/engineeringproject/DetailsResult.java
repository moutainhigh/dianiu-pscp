package com.edianniu.pscp.cs.bean.engineeringproject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.engineeringproject.vo.EngineeringProjectVO;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsVO;

/**
 * 项目详情
 * @author zhoujianjian
 * 2017年9月19日下午11:24:05
 */
public class DetailsResult  extends Result{
	private static final long serialVersionUID = 1L;
	
	private EngineeringProjectVO projectInfo;
	
	private NeedsVO needsInfo;

	public EngineeringProjectVO getProjectInfo() {
		return projectInfo;
	}

	public void setProjectInfo(EngineeringProjectVO projectInfo) {
		this.projectInfo = projectInfo;
	}

	public NeedsVO getNeedsInfo() {
		return needsInfo;
	}

	public void setNeedsInfo(NeedsVO needsInfo) {
		this.needsInfo = needsInfo;
	}
	
	public String ToString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
	
}
