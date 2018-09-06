package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;

public class UnionpayPrepayInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 签名
	 */
	private String signature;
	
	/**
	 * 签名证书，代付使用到
	 */
	private String certId;
	
	/**
	 * 加密证书，代付使用到
	 */
	private String encryptCertId;
	/**
	 * 交易类型
	 */
	private String txnType;
	
	/**
	 * 交易子类
	 */
	private String txnSubType;
	
	/**
	 * 产品类型
	 */
	private String bizType;
	/**
	 * 渠道类型
	 */
	private String channelType;
	/**
	 * 接入类型
	 */
	private String accessType;
	/**
	 * 订单id
	 */
	private String orderId;
	/**
	 * 消费金额，单位为分
	 */
	private Long txnAmt;
	/**
	 * 交易时间
	 */
	private String txnTime;
	/**
	 * 交易超时时间
	 */
	private String payTimeout;
	/**
	 * 消费银行卡号
	 */
	private String accNo;
	
	/**
	 * 订单描述
	 */
	private String orderDesc;
	/**
	 * 银行流水账号，控件支付调用
	 */
	private String tn;	
	/**
	 * 由银联返回，用于在后续类交易中唯一标识一笔交易
	 */
	private String queryId;	
	
	/**
	 *返回码
	 */
	private String respCode;
	/**
	 *返回消息
	 */
	private String respMsg;
	/**
	 *系统跟踪号,收单机构对账时使用，该域由银联系统产生
	 */
	private String traceNo;
	/**
	 *交易传输时间
	 */
	private String traceTime;
	/**
	 *清算时间
	 */
	private String settleDate;
	/**
	 *证件类型，代付使用
	 */
	private String certifTp;
	/**
	 *证件类型号，代付使用
	 */
	private String certifId;
	/**
	 *姓名，代付使用
	 */
	private String customerNm;
	/**
	 *交易类型
	 */
	private Integer type;
	/**
	 *原交易的查询返回码
	 */
	private String origRespCode;
	/**
	 *原交易的查询返回信息
	 */
	private String origRespMsg;
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getCertId() {
		return certId;
	}
	public void setCertId(String certId) {
		this.certId = certId;
	}
	public String getEncryptCertId() {
		return encryptCertId;
	}
	public void setEncryptCertId(String encryptCertId) {
		this.encryptCertId = encryptCertId;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getTxnSubType() {
		return txnSubType;
	}
	public void setTxnSubType(String txnSubType) {
		this.txnSubType = txnSubType;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getAccessType() {
		return accessType;
	}
	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Long getTxnAmt() {
		return txnAmt;
	}
	public void setTxnAmt(Long txnAmt) {
		this.txnAmt = txnAmt;
	}
	public String getTxnTime() {
		return txnTime;
	}
	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}
	public String getPayTimeout() {
		return payTimeout;
	}
	public void setPayTimeout(String payTimeout) {
		this.payTimeout = payTimeout;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	public String getTn() {
		return tn;
	}
	public void setTn(String tn) {
		this.tn = tn;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public String getTraceNo() {
		return traceNo;
	}
	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}
	public String getTraceTime() {
		return traceTime;
	}
	public void setTraceTime(String traceTime) {
		this.traceTime = traceTime;
	}
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	public String getCertifTp() {
		return certifTp;
	}
	public void setCertifTp(String certifTp) {
		this.certifTp = certifTp;
	}
	public String getCertifId() {
		return certifId;
	}
	public void setCertifId(String certifId) {
		this.certifId = certifId;
	}
	public String getCustomerNm() {
		return customerNm;
	}
	public void setCustomerNm(String customerNm) {
		this.customerNm = customerNm;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getOrigRespCode() {
		return origRespCode;
	}
	public void setOrigRespCode(String origRespCode) {
		this.origRespCode = origRespCode;
	}
	public String getOrigRespMsg() {
		return origRespMsg;
	}
	public void setOrigRespMsg(String origRespMsg) {
		this.origRespMsg = origRespMsg;
	}
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	
	
}
