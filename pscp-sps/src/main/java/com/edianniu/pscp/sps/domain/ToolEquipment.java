package com.edianniu.pscp.sps.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-17 15:19:33
 */
public class ToolEquipment extends BaseDo implements Serializable {
    private static final long serialVersionUID = 1L;

    //$column.comments
    private Long id;
    //$column.comments
    private String name;
    //$column.comments
    private String model;
    //$column.comments
    private Date createTime;
    //$column.comments
    private String createUser;
    //$column.comments
    private Date modifiedTime;
    //$column.comments
    private String modifiedUser;
    //$column.comments
    private Long companyId;

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
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：${column.comments}
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：${column.comments}
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * 获取：${column.comments}
     */
    public String getModel() {
        return model;
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

    /**
     * 设置：${column.comments}
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getCompanyId() {
        return companyId;
    }
}
