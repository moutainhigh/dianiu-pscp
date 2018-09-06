package com.edianniu.pscp.sps.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 电工工单视图对象
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-08 17:20:03
 */
public class ElectricianWorkOrderView extends BaseDo implements
        Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String orderId;
    private String checkOption;
    private Integer electriciantype;
    private Long workOrderId;
    private Long socialWorkOrderId;
    private String createUser;
    private String publishTime;
    private Date createTime;
    private Date finishTime;
    private Integer status;
    private Long companyId;
    private String mobile;
    private Long memberId;
    private String txImg;
    private String userName;
    private String name;
    private Long projectId;
    private Long customerId;
    private String projectName;
    private String customerName;
    private Date beginTime;
    private Date cancleTime;
    private Date confirmTime;
    private Date evaluateTime;
    private Integer workOrderLeader;
    private Double actualWorkTime;
    private Double actualFee;
    private Double fee;
    private Integer unit;
    private String content;
    private Date beginWorkTime;
    private Date endWorkTime;
    private String address;
    private Double latitude;
    private Double longitude;
    private Integer type;
    private String title;

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

    public String getCreateUser() {
        return createUser;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public Date getCancleTime() {
        return cancleTime;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public Date getEvaluateTime() {
        return evaluateTime;
    }

    public Integer getWorkOrderLeader() {
        return workOrderLeader;
    }

    public Double getActualWorkTime() {
        return actualWorkTime;
    }

    public Double getActualFee() {
        return actualFee;
    }

    public Double getFee() {
        return fee;
    }

    public Integer getUnit() {
        return unit;
    }

    public String getContent() {
        return content;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public void setCancleTime(Date cancleTime) {
        this.cancleTime = cancleTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public void setEvaluateTime(Date evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    public void setWorkOrderLeader(Integer workOrderLeader) {
        this.workOrderLeader = workOrderLeader;
    }

    public void setActualWorkTime(Double actualWorkTime) {
        this.actualWorkTime = actualWorkTime;
    }

    public void setActualFee(Double actualFee) {
        this.actualFee = actualFee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getBeginWorkTime() {
        return beginWorkTime;
    }

    public Date getEndWorkTime() {
        return endWorkTime;
    }

    public void setBeginWorkTime(Date beginWorkTime) {
        this.beginWorkTime = beginWorkTime;
    }

    public void setEndWorkTime(Date endWorkTime) {
        this.endWorkTime = endWorkTime;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

	public String getTxImg() {
		return txImg;
	}

	public void setTxImg(String txImg) {
		this.txImg = txImg;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
    
}
