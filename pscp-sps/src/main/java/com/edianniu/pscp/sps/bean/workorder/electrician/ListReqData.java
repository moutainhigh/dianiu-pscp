/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月11日 下午6:00:46
 * @version V1.0
 */
package com.edianniu.pscp.sps.bean.workorder.electrician;

import com.edianniu.pscp.sps.commons.Constants;

import java.io.Serializable;

/**
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月11日 下午6:00:46
 */
public class ListReqData implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long companyId;
    private Long projectId;
    private Long customerId;
    private String mobile;
    private String userName;
    private String name;
    private String publishStartTime;
    private String publishEndTime;
    private String finishStartTime;
    private String finishEndTime;
    private String status;
    private Integer pageSize = Constants.DEFAULT_PAGE_SIZE;
    private int offset;
    private Long uid;
    private String latitude;// 经度
    private String longitude;// 纬度

    public Long getCompanyId() {
        return companyId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getMobile() {
        return mobile;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getPublishStartTime() {
        return publishStartTime;
    }

    public String getPublishEndTime() {
        return publishEndTime;
    }

    public String getFinishStartTime() {
        return finishStartTime;
    }

    public String getFinishEndTime() {
        return finishEndTime;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublishStartTime(String publishStartTime) {
        this.publishStartTime = publishStartTime;
    }

    public void setPublishEndTime(String publishEndTime) {
        this.publishEndTime = publishEndTime;
    }

    public void setFinishStartTime(String finishStartTime) {
        this.finishStartTime = finishStartTime;
    }

    public void setFinishEndTime(String finishEndTime) {
        this.finishEndTime = finishEndTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
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
}
