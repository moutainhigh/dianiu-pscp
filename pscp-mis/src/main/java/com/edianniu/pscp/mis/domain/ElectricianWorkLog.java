package com.edianniu.pscp.mis.domain;

/**
 * 电工工作日志信息
 * ClassName: ElectricianWorkLog
 * Author: tandingbo
 * CreateTime: 2017-04-16 18:40
 */
public class ElectricianWorkLog extends BaseDo {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;
    /**
     * 电工工单ID
     */
    private Long electricianWorkOrderId;
    /**
     * 工单ID
     */
    private Long workOrderId;
    /**
     * 电工ID 指电工的uid
     */
    private Long electricianId;
    /**
     * 文本内容
     */
    private String content;
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

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
