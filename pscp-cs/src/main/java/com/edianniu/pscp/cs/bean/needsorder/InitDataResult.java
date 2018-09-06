package com.edianniu.pscp.cs.bean.needsorder;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.sps.bean.project.vo.ProjectVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.CompanyVO;

/**
 * ClassName: InitDataResult
 * Author: tandingbo
 * CreateTime: 2017-10-11 15:48
 */
public class InitDataResult extends Result {
    private static final long serialVersionUID = 1L;

    /* 客户信息.*/
    private CompanyVO customer;
    /* 项目信息.*/
    private ProjectVO project;

    public CompanyVO getCustomer() {
        return customer;
    }

    public void setCustomer(CompanyVO customer) {
        this.customer = customer;
    }

    public ProjectVO getProject() {
        return project;
    }

    public void setProject(ProjectVO project) {
        this.project = project;
    }
}
