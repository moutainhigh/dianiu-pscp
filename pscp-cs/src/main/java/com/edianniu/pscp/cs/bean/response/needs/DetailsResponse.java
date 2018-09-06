package com.edianniu.pscp.cs.bean.response.needs;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.needs.ResponsedCompany;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsVO;
import com.edianniu.pscp.cs.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 需求详情
 * @author zhoujianjian
 * 2017年9月17日下午11:40:55
 */
@JSONMessage(messageCode = 2002125)
public class DetailsResponse extends BaseResponse{
	
	private NeedsVO needs;
	
	private List<ResponsedCompany> responsedCompanys;

	public NeedsVO getNeeds() {
		return needs;
	}

	public void setNeeds(NeedsVO needs) {
		this.needs = needs;
	}

	public List<ResponsedCompany> getResponsedCompanys() {
		return responsedCompanys;
	}

	public void setResponsedCompanys(List<ResponsedCompany> responsedCompanys) {
		this.responsedCompanys = responsedCompanys;
	}
	
	public String ToString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

	

}
