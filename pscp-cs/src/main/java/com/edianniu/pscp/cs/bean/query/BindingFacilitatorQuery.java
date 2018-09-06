package com.edianniu.pscp.cs.bean.query;

import com.edianniu.pscp.cs.commons.BaseQuery;

/**
 * ClassName: BindingFacilitatorQuery
 * Author: tandingbo
 * CreateTime: 2017-10-30 15:03
 */
public class BindingFacilitatorQuery extends BaseQuery {
    private static final long serialVersionUID = 1L;

    private Long uid;
    
    /**
     * 绑定状态
     * 0(邀请，等待客户同意)
     * 1(已绑定，客户已同意)
     * -1(拒绝)
     */
    private Integer status;
    /**
     * 服务商名称
     */
    private String name;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
}
