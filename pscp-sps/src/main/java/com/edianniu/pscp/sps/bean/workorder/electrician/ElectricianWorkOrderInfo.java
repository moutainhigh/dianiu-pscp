/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月11日 下午5:41:56
 * @version V1.0
 */
package com.edianniu.pscp.sps.bean.workorder.electrician;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月11日 下午5:41:56
 */
public class ElectricianWorkOrderInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String orderId;
    private String checkOption;
    private Integer electriciantype;
    private Long workOrderId;
    private Long socialWorkOrderId;
    private String publishTime;
    private Date createTime;
    private Date finishTime;
    private Integer status;
    private Long companyId;
    private String mobile;
    private Long memberId;
    private String userName;
    private String name;
    private Long projectId;
    private Long customerId;
    private String projectName;
    private String customerName;
    private Double actualWorkTime;
    private String actualFee;
    private String fee;
    private Date beginWorkTime;
    private Date endWorkTime;
    private String preFee;//预估费用
    private String latitude;// 经度
    private String longitude;// 纬度
    private String distance;// 距离(小数位2位，单位：m, km)
    private String address;
    private String title;// 标题
    
    private String txImg; // 电工图像

    /* 工单类型名称.*/
    private String typeName;

    public Long getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCheckOption() {
        return checkOption;
    }

    public Integer getElectriciantype() {
        return electriciantype;
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public Long getSocialWorkOrderId() {
        return socialWorkOrderId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public Integer getStatus() {
        return status;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public String getMobile() {
        return mobile;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public Long getProjectId() {
        return projectId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setCheckOption(String checkOption) {
        this.checkOption = checkOption;
    }

    public void setElectriciantype(Integer electriciantype) {
        this.electriciantype = electriciantype;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public void setSocialWorkOrderId(Long socialWorkOrderId) {
        this.socialWorkOrderId = socialWorkOrderId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getActualWorkTime() {
        return actualWorkTime;
    }

    public String getActualFee() {
        return actualFee;
    }

    public String getFee() {
        return fee;
    }

    public void setActualWorkTime(Double actualWorkTime) {
        this.actualWorkTime = actualWorkTime;
    }

    public void setActualFee(String actualFee) {
        this.actualFee = actualFee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public Date getBeginWorkTime() {
        return beginWorkTime;
    }

    public Date getEndWorkTime() {
        return endWorkTime;
    }

    public String getPreFee() {
        return preFee;
    }

    public void setBeginWorkTime(Date beginWorkTime) {
        this.beginWorkTime = beginWorkTime;
    }

    public void setEndWorkTime(Date endWorkTime) {
        this.endWorkTime = endWorkTime;
    }

    public void setPreFee(String preFee) {
        this.preFee = preFee;
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

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

	public String getTxImg() {
		return txImg;
	}

	public void setTxImg(String txImg) {
		this.txImg = txImg;
	}
    
    
}
