package com.edianniu.pscp.cs.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-05 16:20:25
 */
public class WorkOrder extends BaseDo implements Serializable {
    private static final long serialVersionUID = 1L;

    //$column.comments
    private Long id;
    //$column.comments
    private String orderId;
    //$column.comments
    private Long memberId;
    //$column.comments
    private Long engineeringProjectId;
    //$column.comments
    private String name;
    //$column.comments
    private String content;
    //$column.comments
    private String devices;
    //$column.comments
    private Integer type;
    //$column.comments
    private String address;
    //$column.comments
    private Double latitude;
    //$column.comments
    private Double longitude;
    //$column.comments
    private Date startTime;
    //$column.comments
    private Date endTime;
    //$column.comments
    private Date actualStartTime;
    //$column.comments
    private Date actualEndTime;
    //$column.comments
    private Integer status;
    //$column.comments
    private String remark;
    //$column.comments
    private Long companyId;
    //$column.comments
    private String hazardFactorIdentifications;
    private String identificationOther;
    //$column.comments
    private String safetyMeasures;
    private String safetyMeasuresOther;
    //$column.comments
    private String carryingTools;
    /**
     * 评价时间
     */
    private Date appraiserTime;
    /**
     * 评价人
     */
    private String appraiser;

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
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取：${column.comments}
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置：${column.comments}
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * 设置：${column.comments}
     */
    public void setEngineeringProjectId(Long engineeringProjectId) {
        this.engineeringProjectId = engineeringProjectId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getEngineeringProjectId() {
        return engineeringProjectId;
    }

    /**
     * 设置：${column.comments}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：${column.comments}
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：${column.comments}
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取：${column.comments}
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置：${column.comments}
     */
    public void setDevices(String devices) {
        this.devices = devices;
    }

    /**
     * 获取：${column.comments}
     */
    public String getDevices() {
        return devices;
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
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取：${column.comments}
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置：${column.comments}
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * 获取：${column.comments}
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * 设置：${column.comments}
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * 获取：${column.comments}
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * 设置：${column.comments}
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getActualStartTime() {
        return actualStartTime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setActualEndTime(Date actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getActualEndTime() {
        return actualEndTime;
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
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取：${column.comments}
     */
    public String getRemark() {
        return remark;
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

    /**
     * 设置：${column.comments}
     */
    public void setHazardFactorIdentifications(String hazardFactorIdentifications) {
        this.hazardFactorIdentifications = hazardFactorIdentifications;
    }

    /**
     * 获取：${column.comments}
     */
    public String getHazardFactorIdentifications() {
        return hazardFactorIdentifications;
    }

    public String getIdentificationOther() {
        return identificationOther;
    }

    public void setIdentificationOther(String identificationOther) {
        this.identificationOther = identificationOther;
    }

    /**
     * 设置：${column.comments}
     */
    public void setSafetyMeasures(String safetyMeasures) {
        this.safetyMeasures = safetyMeasures;
    }

    /**
     * 获取：${column.comments}
     */
    public String getSafetyMeasures() {
        return safetyMeasures;
    }

    public String getSafetyMeasuresOther() {
        return safetyMeasuresOther;
    }

    public void setSafetyMeasuresOther(String safetyMeasuresOther) {
        this.safetyMeasuresOther = safetyMeasuresOther;
    }

    /**
     * 设置：${column.comments}
     */
    public void setCarryingTools(String carryingTools) {
        this.carryingTools = carryingTools;
    }

    /**
     * 获取：${column.comments}
     */
    public String getCarryingTools() {
        return carryingTools;
    }

    public Date getAppraiserTime() {
        return appraiserTime;
    }

    public void setAppraiserTime(Date appraiserTime) {
        this.appraiserTime = appraiserTime;
    }

    public String getAppraiser() {
        return appraiser;
    }

    public void setAppraiser(String appraiser) {
        this.appraiser = appraiser;
    }
}
