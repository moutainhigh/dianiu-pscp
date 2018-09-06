package com.edianniu.pscp.cs.bean.company.vo;

import java.io.Serializable;

/**
 * ClassName: BindingFacilitatorVO
 * Author: tandingbo
 * CreateTime: 2017-10-30 15:01
 */
public class BindingFacilitatorVO implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
     * 服务商ID
     */
    private Long id;
    /**
     * 服务商名称
     */
    private String name;
    /**
     * 绑定状态
     * 0(邀请，等待客户同意)
     * 1(已绑定，客户已同意)
     * -1(拒绝)
     */
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
