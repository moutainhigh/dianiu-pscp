package com.edianniu.pscp.cs.bean.company;

import java.io.Serializable;

/**
 * ClassName: BindingFacilitatorReqData
 * Author: tandingbo
 * CreateTime: 2017-10-30 14:58
 */
public class BindingFacilitatorReqData implements Serializable {
    private static final long serialVersionUID = 7128122845352617605L;

    private Long uid;
    private Integer offset;
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

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
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
