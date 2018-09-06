package com.edianniu.pscp.mis.bean.electricianworkorder;

import java.io.Serializable;

/**
 * ClassName: OrderDetailInfo
 * Author: tandingbo
 * CreateTime: 2017-04-14 10:15
 */
public class OrderDetailInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 工单ID
     */
    private Long id;
    /**
     * 工单编号
     */
    private String orderId;
    /**
     * 工单名称
     */
    private String name;
    /**
     * 费用(元/天)
     */
    private String fee;
    /**
     * 工作时间(2017-04-01~2017-04-15)
     */
    private String workTime;
    /**
     * 内容
     */
    private String content;
    /**
     * 检修项目
     */
    private String checkOption;
    /**
     * 危险有害因数(json格式)
     */
    private String hazardFactor;
    /**
     * 安全措施(json格式)
     */
    private String safetyMeasures;
    /**
     * 携带机械或设备(json格式)
     */
    private String carryingTools;
    /**
     * 订单发布时间：yyyy-MM-dd hh:MM:ss
     */
    private String pubTime;
    /**
     * 工单状态
     * 0(已派单未确认，社会电工没有这个状态，专属电工有)
     * 1(已确认或者已接单，社会电工接单，专属电工确认)
     * 2(进行中，电工点击开始工作后，状态转为进行中)
     * 3(已完成，电工点击完成工作，服务商待确认)
     * 4(待评价【服务商确认完成】，平台打款给电工)
     * 5(已评价，服务商评价evaluate)
     * -1(已取消，发布人或者电工取消)
     */
    private Integer status;
    /**
     * 项目负责人
     */
    private String projectLeader;
    /**
     * 项目负责人联系电话
     */
    private String contactTel;
    /**
     * 工作地点
     */
    private String address;
    /**
     * 扩展信息，json格式(0：否，1：是)
     * {'isLeader': 0/1, 'isLeaderStart': 0/1}
     */
    private String extendInfo;
    /**
     * 经度
     */
    private String latitude;
    /**
     * 纬度
     */
    private String longitude;
    /**
     * 标题
     */
    private String title;
    /**
     * 需求描述（社会工单）
     */
    private String demandContent;

    /**
     * 自定义危险有害因素辨别
     */
    private String hazardFactorOther;
    /**
     * 自定义安全措施
     */
    private String safetyMeasuresOther;
    /**
     * 工单类型名称
     */
    private String typeName;
    /**
     * 工单类型
     */
    private Integer type;
    /**
     * 实际工单开始时间(yyyy-mm-dd)
     */
    private String actualStartTime;
    /**
     * 实际工单结束时间(yyyy-mm-dd)
     */
    private String actualEndTime;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCheckOption() {
        return checkOption;
    }

    public void setCheckOption(String checkOption) {
        this.checkOption = checkOption;
    }

    public String getHazardFactor() {
        return hazardFactor;
    }

    public void setHazardFactor(String hazardFactor) {
        this.hazardFactor = hazardFactor;
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

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDemandContent() {
        return demandContent;
    }

    public void setDemandContent(String demandContent) {
        this.demandContent = demandContent;
    }

    public String getHazardFactorOther() {
        return hazardFactorOther;
    }

    public void setHazardFactorOther(String hazardFactorOther) {
        this.hazardFactorOther = hazardFactorOther;
    }

    public String getSafetyMeasuresOther() {
        return safetyMeasuresOther;
    }

    public void setSafetyMeasuresOther(String safetyMeasuresOther) {
        this.safetyMeasuresOther = safetyMeasuresOther;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(String actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public String getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(String actualEndTime) {
        this.actualEndTime = actualEndTime;
    }
}
