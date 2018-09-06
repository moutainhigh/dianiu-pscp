package com.edianniu.pscp.renter.mis.bean.renter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.renter.mis.bean.Result;
import com.edianniu.pscp.renter.mis.bean.renter.vo.RenterVO;

/**
 * 门户租客管理--租客详情
 * @author zhoujianjian
 * @date 2018年4月4日 下午3:54:16
 */
public class DetailResult extends Result{

	private static final long serialVersionUID = 1L;
	
	private RenterVO renterVO;

	public RenterVO getRenterVO() {
		return renterVO;
	}

	public void setRenterVO(RenterVO renterVO) {
		this.renterVO = renterVO;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
	
}
