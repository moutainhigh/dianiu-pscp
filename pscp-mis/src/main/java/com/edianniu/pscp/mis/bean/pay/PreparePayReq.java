/**
 * 
 */
package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.user.BaseTerminalData;

/**
 * @author cyl
 *
 */
public class PreparePayReq extends BaseTerminalData implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long uid;
	private Long companyId;
	private String orderId;
	private Integer needInvoice=0;
	/**
	 * 社会工单ID 可以多个组合
	 * 充值订单ID 只能一个或者为空
	 */
	private String orderIds;
	private String amount;
	private Integer payType;//支付类型
	private Integer orderType;//订单类型
	private String ip;
	private String payMethod="APP";//支付方式
	private String returnUrl;//网站支付返回的url
	private String extendParams;//业务扩展参数 256个长度
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
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getExtendParams() {
		return extendParams;
	}
	public void setExtendParams(String extendParams) {
		this.extendParams = extendParams;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getNeedInvoice() {
		return needInvoice;
	}
	public void setNeedInvoice(Integer needInvoice) {
		this.needInvoice = needInvoice;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	
}
