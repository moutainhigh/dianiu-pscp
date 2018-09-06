package com.edianniu.pscp.mis.domain;

/**
 * ClassName: ElectricianEvaluate
 * Author: tandingbo
 * CreateTime: 2017-05-12 10:24
 */
public class ElectricianEvaluate extends BaseDo {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;
    /**
     * 电工工单ID(pscp_electrician_work_order的ID)
     */
    private Long electricianWorkOrderId;
    /**
     * 电工ID
     */
    private Long electricianId;
    /**
     * 内容
     */
    private String content;
    /**
     * 服务质量
     */
    private Long serviceQuality;
    /**
     * 服务速度
     */
    private Long serviceSpeed;
    /**
     * 企业ID
     */
    private Long companyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getElectricianWorkOrderId() {
        return electricianWorkOrderId;
    }

    public void setElectricianWorkOrderId(Long electricianWorkOrderId) {
        this.electricianWorkOrderId = electricianWorkOrderId;
    }

    public Long getElectricianId() {
        return electricianId;
    }

    public void setElectricianId(Long electricianId) {
        this.electricianId = electricianId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getServiceQuality() {
        return serviceQuality;
    }

    public void setServiceQuality(Long serviceQuality) {
        this.serviceQuality = serviceQuality;
    }

    public Long getServiceSpeed() {
        return serviceSpeed;
    }

    public void setServiceSpeed(Long serviceSpeed) {
        this.serviceSpeed = serviceSpeed;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
