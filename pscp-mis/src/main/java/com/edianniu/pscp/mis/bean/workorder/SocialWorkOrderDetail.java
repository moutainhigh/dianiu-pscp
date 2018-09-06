/**
 *
 */
package com.edianniu.pscp.mis.bean.workorder;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author cyl
 */
public class SocialWorkOrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String orderId;
    private String name;
    private String qualifications;//电工资质
    private Integer quantity;//电工人数
    private String title;
    private String content;//工作内容
    private String workTime;//
    private String fee;
    private String pubTime;
    private Integer status;//0招募中，1招募结束，-1招募取消
    private String address;
    private String latitude;
    private String longitude;
    private String distance;
    @JSONField(serialize = false, deserialize = false)
    private Long companyId;
    // 工单类型
    private String typeName;

    public Long getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getName() {
        return name;
    }

    public String getFee() {
        return fee;
    }

    public String getPubTime() {
        return pubTime;
    }

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getDistance() {
        return distance;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getQualifications() {
        return qualifications;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getContent() {
        return content;
    }

    public String getWorkTime() {
        return workTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.DEFAULT_STYLE);
    }
}
