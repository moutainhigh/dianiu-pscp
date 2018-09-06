package com.edianniu.pscp.mis.bean.electricianworkorder;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * ClassName: ListQueryResultInfo
 * Author: tandingbo
 * CreateTime: 2017-04-13 09:44
 */
public class ListQueryResultInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 工单ID
     */
    private Integer id;
    /**
     * 工单编号
     */
    private String orderId;
    /**
     * 工单名称
     */
    private String name;
    /**
     * 工单描述
     */
    private String description;
    /**
     * 工单发布时间
     */
    private String pubTime;
    /**
     * 工单地点
     */
    private String address;
    /**
     * 经度
     */
    private String latitude;
    /**
     * 纬度
     */
    private String longitude;
    /**
     * 距离
     */
    private String distance;
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
     * 检修项目
     */
    private String checkOption;
    /**
     * 标题
     */
    private String title;
    /**
     * 扩展字段
     */
    private String extendInfo;
    /**
     * 工单ID
     */
    private Long workOrderId;
    /**
     * 社会工单ID
     */
    private Long socialWorkOrderId;
    /**
     * 安全措施-自定义内容
     */
    private String safetyMeasuresOther;
    /**
     * 危险有害因数-自定义内容
     */
    private String hazardFactorOther;
    /**
     * 工单负责人（是否工单负责人0否，1是）
     */
    private Integer workOrderLeader;
    /**
     * 工单类型
     */
    @JSONField(serialize = false)
    private Integer type;
    /**
     * 工单类型名称
     */
    private String typeName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public Long getSocialWorkOrderId() {
        return socialWorkOrderId;
    }

    public void setSocialWorkOrderId(Long socialWorkOrderId) {
        this.socialWorkOrderId = socialWorkOrderId;
    }

    public String getSafetyMeasuresOther() {
        return safetyMeasuresOther;
    }

    public void setSafetyMeasuresOther(String safetyMeasuresOther) {
        this.safetyMeasuresOther = safetyMeasuresOther;
    }

    public String getHazardFactorOther() {
        return hazardFactorOther;
    }

    public void setHazardFactorOther(String hazardFactorOther) {
        this.hazardFactorOther = hazardFactorOther;
    }

    public Integer getWorkOrderLeader() {
        return workOrderLeader;
    }

    public void setWorkOrderLeader(Integer workOrderLeader) {
        this.workOrderLeader = workOrderLeader;
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

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
