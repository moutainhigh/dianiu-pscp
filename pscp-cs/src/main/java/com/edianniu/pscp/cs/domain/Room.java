package com.edianniu.pscp.cs.domain;

import java.io.Serializable;

/**
 * 配电房
 *
 * @author zhoujianjian
 * 2017年9月11日下午9:24:49
 */
public class Room extends BaseDo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long companyId;

    private String name;

    private String director;

    private String contactNumber;

    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
