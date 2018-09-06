package com.edianniu.pscp.sps.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-24 14:15:01
 */
public class ElectricianResume extends BaseDo implements Serializable {
    private static final long serialVersionUID = 1L;

    //$column.comments
    private Long id;
    //$column.comments
    private Long electricianId;
    //$column.comments
    private Integer diploma;
    //$column.comments
    private Integer workingYears;
    //$column.comments
    private String city;
    //$column.comments
    private Double expectedFee;
    //$column.comments
    private String synopsis;
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
    public void setElectricianId(Long electricianId) {
        this.electricianId = electricianId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getElectricianId() {
        return electricianId;
    }

    /**
     * 设置：${column.comments}
     */
    public void setDiploma(Integer diploma) {
        this.diploma = diploma;
    }

    /**
     * 获取：${column.comments}
     */
    public Integer getDiploma() {
        return diploma;
    }

    /**
     * 设置：${column.comments}
     */
    public void setWorkingYears(Integer workingYears) {
        this.workingYears = workingYears;
    }

    /**
     * 获取：${column.comments}
     */
    public Integer getWorkingYears() {
        return workingYears;
    }

    /**
     * 设置：${column.comments}
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取：${column.comments}
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置：${column.comments}
     */
    public void setExpectedFee(Double expectedFee) {
        this.expectedFee = expectedFee;
    }

    /**
     * 获取：${column.comments}
     */
    public Double getExpectedFee() {
        return expectedFee;
    }

    /**
     * 设置：${column.comments}
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    /**
     * 获取：${column.comments}
     */
    public String getSynopsis() {
        return synopsis;
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
