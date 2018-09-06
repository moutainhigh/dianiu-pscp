package com.edianniu.pscp.mis.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 工单信息
 * ClassName: WorkOrder
 * Author: tandingbo
 * CreateTime: 2017-04-14 14:04
 */
public class WorkOrder extends BaseDo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;
    /**
     * 工单ID
     */
    private String orderId;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 工程项目ID
     */
    private Long engineeringProjectId;
    /**
     * 工单名称
     */
    private String name;
    /**
     * 工单内容
     */
    private String content;
    /**
     * 检修设备名称
     */
    private String devices;
    /**
     * 工单类型（1：检修(默认)；2：巡视；3：勘察；）
     */
    private Integer type;
    /**
     * 危险有害因数辨识(存储json格式)
     */
    private String hazardFactorIdentifications;
    /**
     * 安全措施(存储json格式)
     */
    private String safetyMeasures;
    /**
     * 携带机械或设备(存储json格式)
     */
    private String carryingTools;
    /**
     * 工作地点
     */
    private String address;
    /**
     * 经度
     */
    private Double latitude;
    /**
     * 纬度
     */
    private Double longitude;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 实际开始时间
     */
    private Date actualStartTime;
    /**
     * 实际结束时间
     */
    private Date actualEndTime;
    /**
     * 状态
     * 0(待确认)，1(已确认)，2(进行中)，3(未评价),4(已评价)，-1(取消)
     * 业务：
     * 未确认(工单生成)，
     * 已确认(所有企业电工已确认)，
     * 已开始(企业负责人已经点击开始工作)，
     * 已完成(3，客户确认)：
     * 待评价-服务商已完成，客户未评价。
     * 已评价-服务商已完成，客户已评价。
     * 取消
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 企业ID
     */
    private Long companyId;
    /**
     * 自定义危险有害因素辨别
     */
    private String identificationOther;
    /**
     * 自定义安全措施
     */
    private String safetyMeasuresOther;
    /**
     * 修复缺陷记录
     */
    private String defectRecords;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getEngineeringProjectId() {
        return engineeringProjectId;
    }

    public void setEngineeringProjectId(Long engineeringProjectId) {
        this.engineeringProjectId = engineeringProjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDevices() {
        return devices;
    }

    public void setDevices(String devices) {
        this.devices = devices;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getHazardFactorIdentifications() {
        return hazardFactorIdentifications;
    }

    public void setHazardFactorIdentifications(String hazardFactorIdentifications) {
        this.hazardFactorIdentifications = hazardFactorIdentifications;
    }

    public String getSafetyMeasures() {
        return safetyMeasures;
    }

    public void setSafetyMeasures(String safetyMeasures) {
        this.safetyMeasures = safetyMeasures;
    }

    public String getCarryingTools() {
        return carryingTools;
    }

    public void setCarryingTools(String carryingTools) {
        this.carryingTools = carryingTools;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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

    public Date getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public Date getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(Date actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getIdentificationOther() {
        return identificationOther;
    }

    public void setIdentificationOther(String identificationOther) {
        this.identificationOther = identificationOther;
    }

    public String getSafetyMeasuresOther() {
        return safetyMeasuresOther;
    }

    public void setSafetyMeasuresOther(String safetyMeasuresOther) {
        this.safetyMeasuresOther = safetyMeasuresOther;
    }

    public String getDefectRecords() {
        return defectRecords;
    }

    public void setDefectRecords(String defectRecords) {
        this.defectRecords = defectRecords;
    }
}
