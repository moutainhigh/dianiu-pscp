package com.edianniu.pscp.renter.mis.bean.renter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.renter.mis.bean.Result;
import com.edianniu.pscp.renter.mis.bean.renter.vo.RenterConfigVO;

public class ConfigDetailResult extends Result{

	private static final long serialVersionUID = 1L;
	
	private RenterConfigVO renterConfigVO;
	
	public RenterConfigVO getRenterConfigVO() {
		return renterConfigVO;
	}

	public void setRenterConfigVO(RenterConfigVO renterConfigVO) {
		this.renterConfigVO = renterConfigVO;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
