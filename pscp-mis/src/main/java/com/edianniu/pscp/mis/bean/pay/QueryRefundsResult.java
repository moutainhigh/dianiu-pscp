package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.Result;


/**
 * 
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年11月7日 上午10:39:11 
 * @version V1.0
 */
public class QueryRefundsResult  extends Result implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<MemberRefundInfo> refunds;
	public List<MemberRefundInfo> getRefunds() {
		return refunds;
	}
	public void setRefunds(List<MemberRefundInfo> refunds) {
		this.refunds = refunds;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	
}
