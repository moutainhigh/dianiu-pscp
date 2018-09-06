package com.edianniu.pscp.cs.bean.workorder.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * ClassName: WorkOrderDetailsVO
 * Author: tandingbo
 * CreateTime: 2017-08-08 11:45
 */
public class WorkOrderDetailsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /* id.*/
    private Long id;
    /* 工单编号.*/
    private String orderId;
    /* 工单名称.*/
    private String name;
    /* 工作时间(yyyy-mm-dd~yyyy-mm-dd).*/
    private String workTime;
    /* 工单描述.*/
    private String content;
    /* 工作地址.*/
    private String address;
    /* 工单发布日期(yyyy-mm-dd).*/
    private String publishTime;
    /* 工单状态.*/
    /* 0: 未确认
    1: 已确认
    2: 进行中
    3: 待评价
    4: 已评价
    -1: 取消*/
    private Integer status;
    /* 检修项目.*/
    /* 格式：[{checkOptionId:'xxx1',name:'xxxx1',personnel:[{nmae:'xx1'id:123},{name:'xx2', id:3434}]},
    {checkOptionId:'xxx1',name:'xxxx2',personnel:[{name:'xx3', id:897},{name:'xx4', id:456}]}]*/
    private String checkOption;
    /* 危险有害因数.*/
    /*json格式(checked:1选中,0未选中)：
    [{id:10001,name:'触电',checked:1}，
    {id:1002,name:'火灾',checked:0},
    {id:-1,name:'自定义',checked:0}]*/
    private String hazardFactor;
    /* 危险有害因数,自定义内容.*/
    private String hazardFactorOther;
    /* 安全措施.*/
    /* json格式(checked:1选中,0未选中)：
    [{id:10001,name:'xxx',checked:1}，
    {id:1002,name:'xxx',checked:0},
    {id:-1,name:'自定义',checked:0}]*/
    private String safetyMeasures;
    /* 安全措施,自定义内容.*/
    private String safetyMeasuresOther;
    /* 携带机械或设备.*/
    /* json格式：
    [{id:10001,name:'xxx'}，
    {id:1002,name:'xxx'}}]*/
    private String carryingTools;
    /* 项目负责人.*/
    private String projectLeader;
    /* 项目负责人联系电话.*/
    private String contactTel;
    /* 经度.*/
    private String latitude;
    /* 纬度.*/
    private String longitude;
    /* 工单实际开始工作时间(yyyy-mm-dd hh:mm:ss).*/
    private String actualStartTime;
    /* 工单实际结束工作时间(yyyy-mm-dd hh:mm:ss).*/
    private String actualEndTime;

    /* 项目名称.*/
    private String projectName;
    /* 服务商ID.*/
    @JSONField(serialize = false)
    private Long companyId;
    /* 项目ID.*/
    @JSONField(serialize = false)
    private Long engineeringProjectId;
    /* 修改缺陷记录.*/
    @JSONField(serialize = false)
    private String defectRecords;
    /* 工单类型.*/
    private Integer type;
    /* 工单类型名称.*/
    private String typeName;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getHazardFactorOther() {
        return hazardFactorOther;
    }

    public void setHazardFactorOther(String hazardFactorOther) {
        this.hazardFactorOther = hazardFactorOther;
    }

    public String getSafetyMeasures() {
        return safetyMeasures;
    }

    public void setSafetyMeasures(String safetyMeasures) {
        this.safetyMeasures = safetyMeasures;
    }

    public String getSafetyMeasuresOther() {
        return safetyMeasuresOther;
    }

    public void setSafetyMeasuresOther(String safetyMeasuresOther) {
        this.safetyMeasuresOther = safetyMeasuresOther;
    }

    public String getCarryingTools() {
        return carryingTools;
    }

    public void setCarryingTools(String carryingTools) {
        this.carryingTools = carryingTools;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getEngineeringProjectId() {
        return engineeringProjectId;
    }

    public void setEngineeringProjectId(Long engineeringProjectId) {
        this.engineeringProjectId = engineeringProjectId;
    }

    public String getDefectRecords() {
        return defectRecords;
    }

    public void setDefectRecords(String defectRecords) {
        this.defectRecords = defectRecords;
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
}
