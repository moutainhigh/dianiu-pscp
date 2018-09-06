/**
 * 
 */
package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author cyl
 *
 */
public class StartPayReq implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long uid;
	private Long companyId;
	private Integer orderType;//1 充值2 社会工单支付3 社会电工工单结算
	/**
	 * 订单编号
	 * orderType=1时，充值，orderIds留空，只能一个
	 * orderType=2时，社会工单订单编号可以多个以逗号隔开
	 * orderType=3时，社会电工工单订单编号可以多个以逗号隔开
	 * orderType=4时，参与需求保证金支付可以多个以逗号隔开
	 * orderType=5时, 项目结算可以多个以逗号隔开
	 * orderType=6时, 电费结算可以多个以逗号隔开
	 */
	private String orderIds;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getOrderIds() {
		return orderIds;
	}
	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}
	
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	
}
