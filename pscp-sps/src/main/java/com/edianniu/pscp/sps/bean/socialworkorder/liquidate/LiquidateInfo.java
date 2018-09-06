package com.edianniu.pscp.sps.bean.socialworkorder.liquidate;

import com.edianniu.pscp.sps.bean.evaluate.electrician.VO.EvaluateVO;

import java.io.Serializable;

/**
 * ClassName: LiquidateInfo
 * Author: tandingbo
 * CreateTime: 2017-07-31 10:35
 */
public class LiquidateInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 电工ID
     */
    private Long electricianId;
    /**
     * 社会工单ID
     */
    private Long socialWorkOrderId;
    /**
     * 电工工单ID
     */
    private Long electricianWorkOrderId;
    /**
     * 实际工作天数
     */
    private Double actualWorkTime;
    /**
     * 评价信息
     */
    private EvaluateVO evaluateInfo;

    public Long getElectricianId() {
        return electricianId;
    }

    public void setElectricianId(Long electricianId) {
        this.electricianId = electricianId;
    }

    public Long getSocialWorkOrderId() {
        return socialWorkOrderId;
    }

    public void setSocialWorkOrderId(Long socialWorkOrderId) {
        this.socialWorkOrderId = socialWorkOrderId;
    }

    public Long getElectricianWorkOrderId() {
        return electricianWorkOrderId;
    }

    public void setElectricianWorkOrderId(Long electricianWorkOrderId) {
        this.electricianWorkOrderId = electricianWorkOrderId;
    }

    public Double getActualWorkTime() {
        return actualWorkTime;
    }

    public void setActualWorkTime(Double actualWorkTime) {
        this.actualWorkTime = actualWorkTime;
    }

    public EvaluateVO getEvaluateInfo() {
        return evaluateInfo;
    }

    public void setEvaluateInfo(EvaluateVO evaluateInfo) {
        this.evaluateInfo = evaluateInfo;
    }


}
