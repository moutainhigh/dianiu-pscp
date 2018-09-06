package com.edianniu.pscp.mis.bean.workorder;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.company.CompanyDetail;

/**
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月13日 下午6:30:26
 * @version V1.0
 */
public class SocialWorkOrderDetailResult extends Result implements Serializable {
	private static final long serialVersionUID = 1L;

	private SocialWorkOrderDetail orderDetail;
	private CompanyDetail companyInfo;

	public SocialWorkOrderDetail getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(SocialWorkOrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

	public CompanyDetail getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyDetail companyInfo) {
		this.companyInfo = companyInfo;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
