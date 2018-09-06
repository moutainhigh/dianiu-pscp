package com.edianniu.pscp.sps.bean.response.socialworkorder;

import com.edianniu.pscp.sps.bean.evaluate.electrician.VO.EvaluateVO;
import com.edianniu.pscp.sps.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: LiquidateDetailsResponse
 * Author: tandingbo
 * CreateTime: 2017-06-30 10:35
 */
@JSONMessage(messageCode = 2002106)
public final class LiquidateDetailsResponse extends BaseResponse {

    /**
     * 电工姓名
     */
    private String electricianName;
    /**
     * 电工ID
     */
    private Long electricianId;
    /**
     * 电工工单ID
     */
    private Long electricianWorkOrderId;
    /**
     * 电工工单编号
     */
    private String electricianWorkOrderNo;
    /**
     * 社会工单ID
     */
    private Long socialWorkOrderId;
    /**
     * 实际工作天数
     */
    private Double actualWorkTime;
    /**
     * 费用(元/天)
     */
    private String fee;
    /**
     * 电工资质
     */
    private String certificate;
    /**
     * 开始时间(yyyy-mm-dd)
     */
    private String beginWorkTime;
    /**
     * 结束时间(yyyy-mm-dd)
     */
    private String endWorkTime;
    /**
     * 费用小计(两位小数)
     */
    private String totalFee;
    /**
     * 评价标识(0:未评价;1:已评价)
     */
    private Integer processed;
    /**
     * 评价信息
     */
    private EvaluateVO evaluate;
    /**
     * 电工资质
     */
    private String qualifications;
    /**
     * 冻结余额
     */
    private String freezingAmount;

    public String getElectricianName() {
        return electricianName;
    }

    public void setElectricianName(String electricianName) {
        this.electricianName = electricianName;
    }

    public Long getElectricianId() {
        return electricianId;
    }

    public void setElectricianId(Long electricianId) {
        this.electricianId = electricianId;
    }

    public Long getElectricianWorkOrderId() {
        return electricianWorkOrderId;
    }

    public void setElectricianWorkOrderId(Long electricianWorkOrderId) {
        this.electricianWorkOrderId = electricianWorkOrderId;
    }

    public String getElectricianWorkOrderNo() {
        return electricianWorkOrderNo;
    }

    public void setElectricianWorkOrderNo(String electricianWorkOrderNo) {
        this.electricianWorkOrderNo = electricianWorkOrderNo;
    }

    public Long getSocialWorkOrderId() {
        return socialWorkOrderId;
    }

    public void setSocialWorkOrderId(Long socialWorkOrderId) {
        this.socialWorkOrderId = socialWorkOrderId;
    }

    public Double getActualWorkTime() {
        return actualWorkTime;
    }

    public void setActualWorkTime(Double actualWorkTime) {
        this.actualWorkTime = actualWorkTime;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getBeginWorkTime() {
        return beginWorkTime;
    }

    public void setBeginWorkTime(String beginWorkTime) {
        this.beginWorkTime = beginWorkTime;
    }

    public String getEndWorkTime() {
        return endWorkTime;
    }

    public void setEndWorkTime(String endWorkTime) {
        this.endWorkTime = endWorkTime;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getProcessed() {
        return processed;
    }

    public void setProcessed(Integer processed) {
        this.processed = processed;
    }

    public EvaluateVO getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(EvaluateVO evaluate) {
        this.evaluate = evaluate;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getFreezingAmount() {
        return freezingAmount;
    }

    public void setFreezingAmount(String freezingAmount) {
        this.freezingAmount = freezingAmount;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
