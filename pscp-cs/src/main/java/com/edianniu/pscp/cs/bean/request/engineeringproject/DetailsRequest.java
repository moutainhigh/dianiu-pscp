package com.edianniu.pscp.cs.bean.request.engineeringproject;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 项目详情
 * @author zhoujianjian
 * 2017年9月19日下午10:54:10
 */
@JSONMessage(messageCode = 1002154)
public class DetailsRequest extends TerminalRequest {
	
	private String projectNo;

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	
	public String ToString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
