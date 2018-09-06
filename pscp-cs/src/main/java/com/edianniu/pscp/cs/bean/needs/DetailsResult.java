package com.edianniu.pscp.cs.bean.needs;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsVO;

/**
 * 需求详情
 * @author zhoujianjian
 * 2017年9月17日下午11:48:49
 */
public class DetailsResult extends Result {
	private static final long serialVersionUID = 1L;
	
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
