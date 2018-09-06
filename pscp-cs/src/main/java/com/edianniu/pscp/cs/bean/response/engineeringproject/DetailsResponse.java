package com.edianniu.pscp.cs.bean.response.engineeringproject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.engineeringproject.vo.EngineeringProjectVO;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsVO;
import com.edianniu.pscp.cs.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;


/**
 * 项目详情
 * @author zhoujianjian
 * 2017年9月19日下午10:56:54
 */
@JSONMessage(messageCode = 2002154)
public class DetailsResponse  extends BaseResponse{
	
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
