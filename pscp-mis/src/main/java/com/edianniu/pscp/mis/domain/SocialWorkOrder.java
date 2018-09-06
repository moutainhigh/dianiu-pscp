/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月13日 下午4:41:41
 * @version V1.0
 */
package com.edianniu.pscp.mis.domain;

import java.util.Date;

/**
 * 社会工单
 *
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月13日 下午4:41:41
 */
public class SocialWorkOrder extends BaseDo {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String orderId;
    private String title;
    private String content;
    private Double totalFee;
    private Double fee;
    private Integer unit;
    private String qualifications;
    private Integer quantity;
    private Integer status;
    private Date expiryTime;
    private Date beginWorkTime;
    private Date endWorkTime;
    private Long workOrderId;
    private Long companyId;
    private String name;
    private String description;
    private String devices;
    private String address;
    private Double latitude;
    private Double longitude;
    private Double distance;
    /**
     * 支付信息冗余字段
     **/
    private Integer payType;
    private Integer payStatus;
    private Double payAmount;
    private Date payTime;
    private Date paySynctime;
    private Date payAsynctime;
    private String payMemo;
    /**退款状态**/
    private Integer refundStatus=0;//0未退款，1已退款
    // 工单类型
    private Integer type;

    public Long getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getContent() {
        return content;
    }

    public Double getFee() {
        return fee;
    }

    public Integer getUnit() {
        return unit;
    }

    public String getQualifications() {
        return qualifications;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getStatus() {
        return status;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public Date getBeginWorkTime() {
        return beginWorkTime;
    }

    public Date getEndWorkTime() {
        return endWorkTime;
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDevices() {
        return devices;
    }

    public String getAddress() {
        return address;
    }

    public Double getDistance() {
        return distance;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    public void setBeginWorkTime(Date beginWorkTime) {
        this.beginWorkTime = beginWorkTime;
    }

    public void setEndWorkTime(Date endWorkTime) {
        this.endWorkTime = endWorkTime;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDevices(String devices) {
        this.devices = devices;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getPayType() {
        return payType;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public Date getPayTime() {
        return payTime;
    }

    public Date getPaySynctime() {
        return paySynctime;
    }

    public Date getPayAsynctime() {
        return payAsynctime;
    }

    public String getPayMemo() {
        return payMemo;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public void setPaySynctime(Date paySynctime) {
        this.paySynctime = paySynctime;
    }

    public void setPayAsynctime(Date payAsynctime) {
        this.payAsynctime = payAsynctime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPayMemo(String payMemo) {
        this.payMemo = payMemo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}
}
