package com.edianniu.pscp.sps.bean.workorder.chieforder;

import com.edianniu.pscp.sps.bean.customer.vo.CustomerVO;
import com.edianniu.pscp.sps.bean.project.vo.ProjectVO;
import com.edianniu.pscp.sps.bean.socialworkorder.recruit.RecruitInfo;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: SaveOrUpdateInfo
 * Author: tandingbo
 * CreateTime: 2017-05-19 14:42
 */
public class SaveOrUpdateInfo implements Serializable {
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
     * 检修——开始时间
     */
    private String startTime;
    /**
     * 检修——结束时间
     */
    private String endTime;
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
     * 负责人ID
     */
    private Long leaderId;
    /**
     * 工单描述
     */
    private String content;
    /**
     * 危险有害因数辨识(存储json格式)
     */
    private String identifications;
    /**
     * 危险有害因数辨识(业主单位负责人填写内容）
     */
    private String identificationText;
    /**
     * 安全措施(存储json格式)
     */
    private String safetyMeasures;
    /**
     * 安全措施其他（业主单位负责人填写内容）
     */
    private String measureText;
    /**
     * 携带机械或设备(存储json格式)
     */
    private String toolequipmentInfo;
    /**
     * 检修项目与电工信息
     */
    private List<ElectricianWorkOrderInfo> electricianWorkOrderInfoList;

    /**
     * 招募信息（社会工单）
     */
    private List<RecruitInfo> socialRecruitList;

    /**
     * 客户信息
     */
    private CustomerVO customerInfo;
    /**
     * 项目信息
     */
    private ProjectVO projectInfo;

    /**
     * 登录用户信息
     */
    private Long uid;
    /**
     * 工单类型
     */
    private Integer type;
    /**
     * 项目维修缺陷记录ID（多个以逗号隔开）
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIdentifications() {
        return identifications;
    }

    public void setIdentifications(String identifications) {
        this.identifications = identifications;
    }

    public String getIdentificationText() {
        return identificationText;
    }

    public void setIdentificationText(String identificationText) {
        this.identificationText = identificationText;
    }

    public String getSafetyMeasures() {
        return safetyMeasures;
    }

    public void setSafetyMeasures(String safetyMeasures) {
        this.safetyMeasures = safetyMeasures;
    }

    public String getMeasureText() {
        return measureText;
    }

    public void setMeasureText(String measureText) {
        this.measureText = measureText;
    }

    public String getToolequipmentInfo() {
        return toolequipmentInfo;
    }

    public void setToolequipmentInfo(String toolequipmentInfo) {
        this.toolequipmentInfo = toolequipmentInfo;
    }

    public List<ElectricianWorkOrderInfo> getElectricianWorkOrderInfoList() {
        return electricianWorkOrderInfoList;
    }

    public void setElectricianWorkOrderInfoList(List<ElectricianWorkOrderInfo> electricianWorkOrderInfoList) {
        this.electricianWorkOrderInfoList = electricianWorkOrderInfoList;
    }

    public List<RecruitInfo> getSocialRecruitList() {
        return socialRecruitList;
    }

    public void setSocialRecruitList(List<RecruitInfo> socialRecruitList) {
        this.socialRecruitList = socialRecruitList;
    }

    public CustomerVO getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerVO customerInfo) {
        this.customerInfo = customerInfo;
    }

    public ProjectVO getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(ProjectVO projectInfo) {
        this.projectInfo = projectInfo;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDefectRecords() {
        return defectRecords;
    }

    public void setDefectRecords(String defectRecords) {
        this.defectRecords = defectRecords;
    }
}
