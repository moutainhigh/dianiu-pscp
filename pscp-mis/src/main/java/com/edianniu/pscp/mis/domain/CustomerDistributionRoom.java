package com.edianniu.pscp.mis.domain;

import java.io.Serializable;

/**
 * ${comments}
 *
 * @author wangcheng.li
 * @email wangcheng.li@edianniu.com
 * @date 2017-09-18 10:33:12
 */
public class CustomerDistributionRoom extends BaseDo implements Serializable {
    private static final long serialVersionUID = 1L;

    //$column.comments
    private Long id;
    //$column.comments
    private Long companyId;
    //$column.comments
    private String name;
    //$column.comments
    private String director;
    //$column.comments
    private String contactNumber;
    //$column.comments
    private String address;

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
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getCompanyId() {
        return companyId;
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
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * 获取：${column.comments}
     */
    public String getDirector() {
        return director;
    }

    /**
     * 设置：${column.comments}
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * 获取：${column.comments}
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * 设置：${column.comments}
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取：${column.comments}
     */
    public String getAddress() {
        return address;
    }
}
