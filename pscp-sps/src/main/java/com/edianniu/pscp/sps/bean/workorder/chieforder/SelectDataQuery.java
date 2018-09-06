package com.edianniu.pscp.sps.bean.workorder.chieforder;

import com.edianniu.pscp.sps.commons.BaseQuery;

/**
 * ClassName: SelectDataQuery
 * Author: tandingbo
 * CreateTime: 2017-07-18 16:49
 */
public class SelectDataQuery extends BaseQuery {
	private static final long serialVersionUID = 1L;
	/* 工单名称. */
    private String name;
    /* 服务商ID. */
    private Long companyId;
    /* 工单状态. */
    private Integer[] status;
    /* 项目ID*/
    private Long projectId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer[] getStatus() {
        return status;
    }

    public void setStatus(Integer[] status) {
        this.status = status;
    }

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
}
