package com.edianniu.pscp.cs.bean.engineeringproject;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目需求信息
 * @author zhoujianjian
 * 2017年9月18日下午11:14:23
 */
public class EngineerProjectInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	//$column.comments
    private Long id;
    //项目编号
    private String projectNo;
    //$column.comments
    private Long customerId;
    //$column.comments
    private String name;
    //$column.comments
    private String description;
    //$column.comments
    private Date startTime;
	//$column.comments
    private Date endTime;
    //$column.comments
    private Integer sceneInvestigation;
    //$column.comments
    private String contractFileId;
    //$column.comments
    private Long companyId;
    //项目状态   0：进行中；    1：费用待确认    2：支付失败    3：已结算
    private Integer status;
    //需求ID
    private Long needsId;
    
    private String roomIds;
	//$column.comments
    private Date createTime;
    //$column.comments
    private String createUser;
    //$column.comments
    private Integer deleted;
    //实际结算金额
    private String actualSettlementAmount;
    //支付类型 0:无    1:余额    2:支付宝     3:微信支付           4：银联支付
    private Integer payType; 
    //支付状态    0:未支付    1:支付确认 (客户端)   2:支付成功(服务端异步通知)  3:支付失败      4:取消支付
    private Integer payStatus;
    //支付金额
    private String payAmount;
    //支付时间  发起支付时间
    private Date payTime;
    //支付时间   成功时间(同步)
    private Date paySyncTime;
    //支付时间   成功时间(异步)
    private Date payAsyncTime;
    //支付备注  
    private String payMemo;
    
	public Integer getDeleted() {
		return deleted;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getSceneInvestigation() {
		return sceneInvestigation;
	}
	public String getActualSettlementAmount() {
		return actualSettlementAmount;
	}
	public void setActualSettlementAmount(String actualSettlementAmount) {
		this.actualSettlementAmount = actualSettlementAmount;
	}
	public void setSceneInvestigation(Integer sceneInvestigation) {
		this.sceneInvestigation = sceneInvestigation;
	}
	public String getContractFileId() {
		return contractFileId;
	}
	public void setContractFileId(String contractFileId) {
		this.contractFileId = contractFileId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getNeedsId() {
		return needsId;
	}
	public void setNeedsId(Long needsId) {
		this.needsId = needsId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public Date getPaySyncTime() {
		return paySyncTime;
	}
	public void setPaySyncTime(Date paySyncTime) {
		this.paySyncTime = paySyncTime;
	}
	public Date getPayAsyncTime() {
		return payAsyncTime;
	}
	public void setPayAsyncTime(Date payAsyncTime) {
		this.payAsyncTime = payAsyncTime;
	}
	public String getPayMemo() {
		return payMemo;
	}
	public void setPayMemo(String payMemo) {
		this.payMemo = payMemo;
	}
	public String getRoomIds() {
		return roomIds;
	}
	public void setRoomIds(String roomIds) {
		this.roomIds = roomIds;
	}
	

}
