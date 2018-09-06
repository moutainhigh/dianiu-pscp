package com.edianniu.pscp.mis.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: ElectricianWorkOrder
 * Author: tandingbo
 * CreateTime: 2017-04-13 10:52
 */
public class ElectricianWorkOrder extends BaseDo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;
    /**
     * 工单ID
     */
    private Long workOrderId;
    /**
     * 电工工单ID
     */
    private String orderId;
    /**
     * 检修项目
     */
    private String checkOption;
    /**
     * 类型(1企业电工工单，2社会电工工单)
     */
    private Integer type;
    /**
     * 社会电工订单ID
     * type为1时，0
     * type为2时，为pscp_social_work_order的ID
     */
    private Long socialWorkOrderId;
    /**
     * 工单负责人(是否工单负责人0否，1是)
     */
    private Integer workOrderLeader;
    /**
     * 电工ID(member_id)
     */
    private Long electricianId;
    /**
     * 状态
     * 0(已派单未确认，社会电工没有这个状态，专属电工有)
     * 1(已确认或者已接单，社会电工接单，专属电工确认)
     * 2(进行中，电工点击开始工作后，状态转为进行中)
     * 3(已完成，电工点击完成工作，服务商待确认)
     * 4(待评价【服务商确认完成】，平台打款给电工)
     * 5(已评价，服务商评价evaluate)
     * -1(已取消，发布人或者电工取消)
     * -2(不符合)
     */
    private Integer status;
    /**
     * 结算支付状态
     * 0 结算未支付
     * 1 结算已支付
     */
    private Integer settlementPayStatus=0;
    /**
     * 确认时间
     */
    private Date confirmTime;
    /**
     * 开始时间
     */
    private Date beginTime;
    /**
     * 完成时间
     */
    private Date finishTime;
    /**
     * 评价时间
     */
    private Date evaluateTime;
    /**
     * 取消时间
     */
    private Date cancleTime;
    /**
     * 备注
     */
    private String memo;
    /**
     * 企业ID(默认0：平台，其他是指企业的电工)
     */
    private Long companyId;
    /**
     * 实际费用
     */
    private Double actualFee;
    /**
     * 实际工时(服务商最终确认)
     */
    private Double actualWorkTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCheckOption() {
        return checkOption;
    }

    public void setCheckOption(String checkOption) {
        this.checkOption = checkOption;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getSocialWorkOrderId() {
        return socialWorkOrderId;
    }

    public void setSocialWorkOrderId(Long socialWorkOrderId) {
        this.socialWorkOrderId = socialWorkOrderId;
    }

    public Integer getWorkOrderLeader() {
        return workOrderLeader;
    }

    public void setWorkOrderLeader(Integer workOrderLeader) {
        this.workOrderLeader = workOrderLeader;
    }

    public Long getElectricianId() {
        return electricianId;
    }

    public void setElectricianId(Long electricianId) {
        this.electricianId = electricianId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(Date evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    public Date getCancleTime() {
        return cancleTime;
    }

    public void setCancleTime(Date cancleTime) {
        this.cancleTime = cancleTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Double getActualFee() {
        return actualFee;
    }

    public void setActualFee(Double actualFee) {
        this.actualFee = actualFee;
    }

    public Double getActualWorkTime() {
        return actualWorkTime;
    }

    public void setActualWorkTime(Double actualWorkTime) {
        this.actualWorkTime = actualWorkTime;
    }
	public Integer getSettlementPayStatus() {
		return settlementPayStatus;
	}

	public void setSettlementPayStatus(Integer settlementPayStatus) {
		this.settlementPayStatus = settlementPayStatus;
	}

}
