package com.edianniu.pscp.cs.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-08 17:20:03
 */
public class ElectricianWorkOrder extends BaseDo implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long id;

    private Long workOrderId;

    private String orderId;

    private String checkOptionId;

    private String checkOption;

    private Integer type;

    private Long socialWorkOrderId;

    private Long workOrderLeader;

    private Long electricianId;

    private Integer status;

    private Date confirmTime;

    private Date beginTime;

    private Date finishTime;

    private Date evaluateTime;

    private Date cancleTime;

    private String memo;

    private Long companyId;

    private Double actualWorkTime;
    private Double actualFee;
    /**
     * 工单结算支付状态(0:结算未支付,1:结算已支付)
     */
    private Integer settlementPayStatus;

    /**
     * 设置：${column.comments}
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：${column.comments}
     */
    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getWorkOrderId() {
        return workOrderId;
    }

    /**
     * 设置：${column.comments}
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取：${column.comments}
     */
    public String getOrderId() {
        return orderId;
    }

    public String getCheckOptionId() {
        return checkOptionId;
    }

    public void setCheckOptionId(String checkOptionId) {
        this.checkOptionId = checkOptionId;
    }

    /**
     * 设置：${column.comments}
     */
    public void setCheckOption(String checkOption) {
        this.checkOption = checkOption;
    }

    /**
     * 获取：${column.comments}
     */
    public String getCheckOption() {
        return checkOption;
    }

    /**
     * 设置：${column.comments}
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取：${column.comments}
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置：${column.comments}
     */
    public void setSocialWorkOrderId(Long socialWorkOrderId) {
        this.socialWorkOrderId = socialWorkOrderId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getSocialWorkOrderId() {
        return socialWorkOrderId;
    }

    /**
     * 设置：${column.comments}
     */
    public void setWorkOrderLeader(Long workOrderLeader) {
        this.workOrderLeader = workOrderLeader;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getWorkOrderLeader() {
        return workOrderLeader;
    }

    /**
     * 设置：${column.comments}
     */
    public void setElectricianId(Long electricianId) {
        this.electricianId = electricianId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getElectricianId() {
        return electricianId;
    }

    /**
     * 设置：${column.comments}
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：${column.comments}
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：${column.comments}
     */
    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getConfirmTime() {
        return confirmTime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setEvaluateTime(Date evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getEvaluateTime() {
        return evaluateTime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setCancleTime(Date cancleTime) {
        this.cancleTime = cancleTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getCancleTime() {
        return cancleTime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * 获取：${column.comments}
     */
    public String getMemo() {
        return memo;
    }

    /**
     * 设置：${column.comments}
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getCompanyId() {
        return companyId;
    }

    public Double getActualWorkTime() {
        return actualWorkTime;
    }

    public void setActualWorkTime(Double actualWorkTime) {
        this.actualWorkTime = actualWorkTime;
    }

    public Double getActualFee() {
        return actualFee;
    }

    public void setActualFee(Double actualFee) {
        this.actualFee = actualFee;
    }

    public Integer getSettlementPayStatus() {
        return settlementPayStatus;
    }

    public void setSettlementPayStatus(Integer settlementPayStatus) {
        this.settlementPayStatus = settlementPayStatus;
    }
}
