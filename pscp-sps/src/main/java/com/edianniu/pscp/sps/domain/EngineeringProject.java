package com.edianniu.pscp.sps.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-15 15:38:18
 */
public class EngineeringProject extends BaseDo implements Serializable {
    private static final long serialVersionUID = 1L;

    //$column.comments
    private Long id;
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

    private String projectNo;
    private Integer status;
    private Long needsId;
    private String roomIds;
    private Double actualSettlementAmount;
    private Integer payType;
    private Integer payStatus;
    private Double payAmount;
    private Date payTime;
    private Date paySyncTime;
    private Date payAsyncTime;
    private String payMemo;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setSceneInvestigation(Integer sceneInvestigation) {
        this.sceneInvestigation = sceneInvestigation;
    }

    public Integer getSceneInvestigation() {
        return sceneInvestigation;
    }

    public void setContractFileId(String contractFileId) {
        this.contractFileId = contractFileId;
    }

    public String getContractFileId() {
        return contractFileId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
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

    public Double getActualSettlementAmount() {
        return actualSettlementAmount;
    }

    public void setActualSettlementAmount(Double actualSettlementAmount) {
        this.actualSettlementAmount = actualSettlementAmount;
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

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
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
