package com.edianniu.pscp.mis.domain;

import java.util.Date;

/**
 * 工程项目信息表
 * ClassName: EngineeringProject
 * Author: tandingbo
 * CreateTime: 2017-04-18 10:02
 */
public class EngineeringProject extends BaseDo {

	private static final long serialVersionUID = 1L;
	private Long id;
    //项目编号
    private String projectNo;
    
    private Long customerId;
    
    private String name;
    
    private String description;
    
    private Date startTime;
    
    private Date endTime;
    
    private Integer sceneInvestigation;
    
    private String contractFileId;
    
    private Long companyId;
    //项目状态   0：进行中；    1：费用待确认    2：支付失败    3：已结算
    private Integer status;
    //需求ID
    private Long needsId;
	
    //实际结算金额
    private Double actualSettlementAmount;
    //支付类型 0:无    1:余额    2:支付宝     3:微信支付           4：银联支付
    private Integer payType; 
    //支付状态    0:未支付    1:支付确认 (客户端)   2:支付成功(服务端异步通知)  3:支付失败      4:取消支付
    private Integer payStatus;
    //支付金额
    private Double payAmount;
    //支付时间  发起支付时间
    private Date payTime;
    //支付时间   成功时间(同步)
    private Date paySyncTime;
    //支付时间   成功时间(异步)
    private Date payAsyncTime;
    //支付备注  
    private String payMemo;
	public Long getId() {
		return id;
	}
	public String getProjectNo() {
		return projectNo;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public Date getStartTime() {
		return startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public Integer getSceneInvestigation() {
		return sceneInvestigation;
	}
	public String getContractFileId() {
		return contractFileId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public Integer getStatus() {
		return status;
	}
	public Long getNeedsId() {
		return needsId;
	}
	public Double getActualSettlementAmount() {
		return actualSettlementAmount;
	}
	public Integer getPayType() {
		return payType;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public Double getPayAmount() {
		return payAmount;
	}
	public Date getPayTime() {
		return payTime;
	}
	public Date getPaySyncTime() {
		return paySyncTime;
	}
	public Date getPayAsyncTime() {
		return payAsyncTime;
	}
	public String getPayMemo() {
		return payMemo;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setSceneInvestigation(Integer sceneInvestigation) {
		this.sceneInvestigation = sceneInvestigation;
	}
	public void setContractFileId(String contractFileId) {
		this.contractFileId = contractFileId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setNeedsId(Long needsId) {
		this.needsId = needsId;
	}
	public void setActualSettlementAmount(Double actualSettlementAmount) {
		this.actualSettlementAmount = actualSettlementAmount;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public void setPaySyncTime(Date paySyncTime) {
		this.paySyncTime = paySyncTime;
	}
	public void setPayAsyncTime(Date payAsyncTime) {
		this.payAsyncTime = payAsyncTime;
	}
	public void setPayMemo(String payMemo) {
		this.payMemo = payMemo;
	}

}
