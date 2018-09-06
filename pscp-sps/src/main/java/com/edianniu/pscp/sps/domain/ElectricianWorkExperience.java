package com.edianniu.pscp.sps.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-24 15:18:21
 */
public class ElectricianWorkExperience extends BaseDo implements Serializable {
    private static final long serialVersionUID = 1L;

    //$column.comments
    private Long id;
    //$column.comments
    private Long resumeId;
    //$column.comments
    private String companyName;
    //$column.comments
    private String workContent;
    //$column.comments
    private Date startTime;
    //$column.comments
    private Date endTime;
    //$column.comments
    private Integer orderNum;
    //$column.comments
    private String ext;
    //$column.comments
    private Date createTime;
    //$column.comments
    private String createUser;
    //$column.comments
    private Date modifiedTime;
    //$column.comments
    private String modifiedUser;

    /**
     * 设置：${column.comments}
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：${column.comments}
     */
    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getResumeId() {
        return resumeId;
    }

    /**
     * 设置：${column.comments}
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 获取：${column.comments}
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 设置：${column.comments}
     */
    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    /**
     * 获取：${column.comments}
     */
    public String getWorkContent() {
        return workContent;
    }

    /**
     * 设置：${column.comments}
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取：${column.comments}
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * 设置：${column.comments}
     */
    public void setExt(String ext) {
        this.ext = ext;
    }

    /**
     * 获取：${column.comments}
     */
    public String getExt() {
        return ext;
    }

    /**
     * 设置：${column.comments}
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 获取：${column.comments}
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 设置：${column.comments}
     */
    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getModifiedTime() {
        return modifiedTime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    /**
     * 获取：${column.comments}
     */
    public String getModifiedUser() {
        return modifiedUser;
    }

}
